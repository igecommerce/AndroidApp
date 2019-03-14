package kite.amibee.com.netstore.activity.register;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonObject;

import java.math.BigInteger;
import java.nio.file.Path;
import java.util.List;

import javax.security.auth.login.LoginException;

import kite.amibee.com.netstore.activity.register.Forgot_password;
import kite.amibee.com.netstore.PaymentActivity;
import kite.amibee.com.netstore.SelectAddressActivity;
import kite.amibee.com.netstore.activity.home.HomeActivity;
import kite.amibee.com.netstore.R;
import kite.amibee.com.netstore.model.pojo.AddressListModel;
import kite.amibee.com.netstore.model.pojo.AddressModel;
import kite.amibee.com.netstore.model.pojo.signin.SignInDetailsModel;
import kite.amibee.com.netstore.model.pojo.signin.SignInModel;
import kite.amibee.com.netstore.util.PreferencesHelper;
import kite.amibee.com.netstore.util.Utils;
import kite.amibee.com.netstore.util.api.Api;

public class SignInActivity extends AppCompatActivity {
    private static final String TAG = "SignInActivity";
    Button login_bt_submit;
    TextView login_tv_signup,login_tv_forgot;
    EditText et_signin_email, et_signin_pass;
    TextInputLayout ti_signin_email,ti_signin_pass;
    private View.OnClickListener onClickListener;
    View.OnFocusChangeListener onFocusChangeListener;
    TextWatcher textWatcher;
    Editable editable;
    String s_email;
    String s_pass;
    Utils utils;
    Api api;
    PreferencesHelper preferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        //getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        init();

    }

    public void init() {
        preferencesHelper = new PreferencesHelper(this);
        api = new Api(this, "");
        utils = new Utils();

        onFocusChangeListener();
        textWatcher();
        onClick();
        login_bt_submit = (Button) findViewById(R.id.login_bt_submit);
        login_tv_signup = (TextView) findViewById(R.id.login_tv_signup);
        login_tv_forgot = (TextView) findViewById(R.id.login_tv_forgot);
        et_signin_email = findViewById(R.id.et_signin_email);
        et_signin_pass = findViewById(R.id.et_signin_pass);
        ti_signin_email=findViewById(R.id.ti_signin_email);
        ti_signin_pass=findViewById(R.id.ti_signin_pass);

        login_tv_signup.setOnClickListener(onClickListener);
       // login_tv_forgot.setOnClickListener(onClickListener);
        login_bt_submit.setOnClickListener(onClickListener);
        et_signin_email.setOnClickListener(onClickListener);
        et_signin_pass.setOnClickListener(onClickListener);

        et_signin_email.addTextChangedListener(textWatcher);
        et_signin_pass.addTextChangedListener(textWatcher);


        et_signin_email.setOnFocusChangeListener(onFocusChangeListener);
        et_signin_pass.setOnFocusChangeListener(onFocusChangeListener);

    }

    public void validationEnable(Editable s, String msg){

        if (!TextUtils.isEmpty(s) && msg==null ) {


            if(et_signin_email.getText().hashCode()==s.hashCode()){
                ti_signin_email.setError(null);
            }else if (et_signin_pass.getText().hashCode()==s.hashCode()){
                ti_signin_pass.setError(null);
            }

        }else if (TextUtils.isEmpty(s) && msg!=null){
            Log.e(TAG, "validationEnable: else if 1 " );
            if(et_signin_email.getText().hashCode()==s.hashCode()){
                ti_signin_email.setError(msg);
            }else if (et_signin_pass.getText().hashCode()==s.hashCode()){
                ti_signin_pass.setError(msg);
            }
        }
        else{
            Log.e(TAG, "validationEnable: else " );
            if(et_signin_email.getText().hashCode()==s.hashCode()){
                ti_signin_email.setError(msg);
            }else if (et_signin_pass.getText().hashCode()==s.hashCode()){
                ti_signin_pass.setError(msg);
            }
        }
    }

    public void onFocusChangeListener(){
        onFocusChangeListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    Log.e(TAG, "onFocusChange: " );
                    if (((EditText)view).getText().length()==0){
                        if(et_signin_email.getText().hashCode()==view.hashCode()){
                            validationEnable(((EditText)view).getText(),getResources().getString(R.string.err_valid_email));
                        }else if (et_signin_pass.getText().hashCode()==view.hashCode()){
                            validationEnable(((EditText)view).getText(),getResources().getString(R.string.err_valid_pass));
                        }

                    }else {
                        validationEnable(((EditText)view).getText(),null);
                    }

                }else {

                }
            }
        };
    }
    public void textWatcher(){
        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable edit) {
                editable=edit;
                if (edit.length()==0){
                    if(et_signin_email.getText().hashCode()==edit.hashCode()){
                        validationEnable(edit,getResources().getString(R.string.err_valid_email));
                    }else if (et_signin_pass.getText().hashCode()==edit.hashCode()){
                        validationEnable(edit,getResources().getString(R.string.err_valid_pass));
                    }


                }else {
                    validationEnable(edit,null);
                }

            }
        };
    }
    public void onClick() {
        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (login_bt_submit.hashCode() == view.hashCode()) {
                    s_email = et_signin_email.getText().toString();
                    s_pass = et_signin_pass.getText().toString();

                    if (s_email.isEmpty()){
                        ti_signin_email.setError(getResources().getString(R.string.err_valid_email));
                    }

                    if (s_pass.isEmpty()){
                        ti_signin_pass.setError(getResources().getString(R.string.err_valid_pass));
                    }

                    if (!s_email.isEmpty() && !s_pass.isEmpty()) {
                        if (s_email.matches(utils.emailPattern())){
                            signIn(s_email, s_pass);
                        }else {
                            ti_signin_email.setError(getResources().getString(R.string.err_valid_email_pattern));
                        }


                    }


                } else if (login_tv_signup.hashCode() == view.hashCode()) {
                    Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                    startActivity(intent);
                    finish();
                }/*else if (login_tv_forgot.hashCode() == view.hashCode()) {
                    Intent intent = new Intent(getApplicationContext(), Forgot_password.class);
                    startActivity(intent);
                    finish();
                }*/
            }
        };
    }



    public void signIn(String email, String pass) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", email);
        jsonObject.addProperty("password", pass);
        api.signIn(jsonObject);
    }

    public void apiResponse(SignInModel apiResponse) {
        String msg = apiResponse.getMessage();
        Log.e(TAG, "apiResponse: get message "+apiResponse.getMessage() );
        try{
            if (msg == null || msg.isEmpty()) {
                SignInDetailsModel signInDetailsModel = apiResponse.getCustomer();
                String userActive = signInDetailsModel.getActive();
                String cusId = signInDetailsModel.getCustomerId();
                String quoteId = signInDetailsModel.getQuoteId();
                String userName = signInDetailsModel.getUsername();
                String email = signInDetailsModel.getEmail();
                String mobileNum=signInDetailsModel.getMobile();
                long mobile =0;
                if (mobileNum!=null && !mobileNum.isEmpty()){

                    mobile = Long.parseLong(mobileNum);
                    Log.e(TAG, "apiResponse: signin mobile "+mobile );
                }

                Log.e(TAG, "apiResponse: signin userActive "+userActive );
                Log.e(TAG, "apiResponse: signin cusId "+cusId );
                Log.e(TAG, "apiResponse: signin quoteId "+quoteId );
                Log.e(TAG, "apiResponse: signin userName "+userName );
                Log.e(TAG, "apiResponse: signin email "+email );
                Log.e(TAG, "apiResponse: signin mobile "+mobile );
                if (cusId==null){
                    cusId="0";
                }
                preferencesHelper.putUserRegister(Boolean.parseBoolean(userActive));
                preferencesHelper.setCusId(Integer.parseInt(cusId));

//            preferencesHelper.setQuoteId(Integer.parseInt(quoteId));
                preferencesHelper.setUserName(userName);
                preferencesHelper.setEmail(email);
                preferencesHelper.setMobile(mobile);
                Log.d(TAG, "apiResponse: signin after cusId "+preferencesHelper.getCusId() );
                //just temp ..after you should  delete
//                preferencesHelper.setAddressId(34);

            /*CusID=preferencesHelper.getCusId();
            api.addressList(CusID);*/
                Log.e(TAG, "Signin successful "+userActive );
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                finish();
            } else {
                utils.showMessage(msg, SignInActivity.this);
            }

        }catch (Exception e){
            Log.e(TAG, "apiResponse: Exception "+e );
        }


    }

    /*public void apiResponseAdressList(AddressListModel apiDataAddreList){
       List<AddressModel> addressList = apiDataAddreList.getAddressList();
       if (addressList.size()< 0){
           String name="Manikandan";
           String street="Anna Nagar";
           JsonObject jsonObject = new JsonObject();
           jsonObject.addProperty("custId", CusID);
           jsonObject.addProperty("firstname", name);
           jsonObject.addProperty("streetname", street);
           jsonObject.addProperty("countryId", 1);
           jsonObject.addProperty("regionId", 1);
           jsonObject.addProperty("areaId", 1);
           jsonObject.addProperty("postcode", 1);
           api.addressAdd(jsonObject);
           Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
           startActivity(intent);
           finish();
       }

    }
    public void apiResponseAddAdress(AddressModel addressModel){
        int addressId = Integer.parseInt(addressModel.getId());
        preferencesHelper.setAddressId(addressId);
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        finish();
    }*/


}
