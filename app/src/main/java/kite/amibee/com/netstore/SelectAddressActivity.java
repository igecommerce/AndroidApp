package kite.amibee.com.netstore;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kite.amibee.com.netstore.activity.addToCart.MyCartActivity;
import kite.amibee.com.netstore.activity.product.ProductFullViewActivity;
import kite.amibee.com.netstore.adapter.AddressSelectAdapter;
import kite.amibee.com.netstore.model.AddressSelectModel;
import kite.amibee.com.netstore.model.pojo.AddressListModel;
import kite.amibee.com.netstore.model.pojo.AddressModel;
import kite.amibee.com.netstore.model.pojo.AddressSingleUserModel;
import kite.amibee.com.netstore.util.PreferencesHelper;
import kite.amibee.com.netstore.util.api.Api;
import kite.amibee.com.netstore.util.api.ApiClient;
import kite.amibee.com.netstore.util.api.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectAddressActivity extends AppCompatActivity {
    private static final String TAG = "SelectAddressActivity";
   // private static final String[] state_list = {"state 1", "state 2", "state 3"};
    //private static final String[] country_list = {"country 1", "country 2", "country 3"};
    /*toolbar*/
    ImageView toolbar_menu_iv, toolbar_logo_iv, toolbar_back_iv, toolbar_cart_iv;
    TextView toolbar_tv_title, tv_addr_pincode, tv_addr_addrss_default, tv_address_title;
    RelativeLayout toolbar_lay_cart;
    RadioButton rb_addre_new;
    TextInputEditText et_addnew_altername_ph, et_addnew_land, et_addnew_city, et_addnew_str, et_add_newaddre_ph,
            et_add_newaddre_name;
    EditText et_addnew_addre_addre;
    RelativeLayout lay_addre_savedelivery, lay_addre_cancel, lay_add_new_address;
    RecyclerView rv_address_select;
    AddressSelectAdapter addressSelectAdapter;
    List<AddressSingleUserModel> addressList = new ArrayList<>();
    int addressDefault = -1;
    //toolbar
    //ui
    String countryId,stateId,cityId;
    TextView s_addr_tv_agree;
    Api api;
    PreferencesHelper preferencesHelper;
    int customerID;
    int addressId = 0;
    private ApiInterface apiInterface;
    private Spinner dropdown_state_spinner, dropdown_country_spinner,dropdown_city_spinner;
    private View.OnClickListener onClickListener;
    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener;
    private List<String> countriesList = new ArrayList<>(), idCountriesList = new ArrayList<>(),
            statesList = new ArrayList<>(), idStatesList = new ArrayList<>(),citiesList = new ArrayList<>(), idCitiesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_address);
        //getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        init();

        getCountries();

        dropdown_country_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                countryId = idCountriesList.get(position);
                Log.d(TAG, "cId" + countryId);

                getStates(countryId);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dropdown_state_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stateId = idStatesList.get(position);
                Log.d(TAG, "sId" + stateId);

                getCity(countryId,stateId);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        dropdown_city_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cityId = idCitiesList.get(position);
                Log.d(TAG, "cityId" + cityId);

                //getCities(stateId);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ((ImageView) findViewById(R.id.toolbar_cart)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(ProductFullViewActivity.this, "jjjjjjjj", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SelectAddressActivity.this, MyCartActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

    }

    public void init() {
        preferencesHelper = new PreferencesHelper(this);
        api = new Api(this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        customerID = preferencesHelper.getCusId();
        Log.w(TAG, "init: customerID " + customerID);
        api.addressSingleUser(customerID);

        onClick();
        rbListener();
        //toolbar
        toolbar_menu_iv = (ImageView) findViewById(R.id.toolbar_menu);
        toolbar_logo_iv = (ImageView) findViewById(R.id.toolbar_logo);
        toolbar_back_iv = (ImageView) findViewById(R.id.toolbar_back);
        toolbar_tv_title = (TextView) findViewById(R.id.toolbar_tv_title);
        toolbar_cart_iv = (ImageView) findViewById(R.id.toolbar_cart);
        tv_address_title = findViewById(R.id.tv_address_title);
        toolbar_cart_iv.setVisibility(View.GONE);
        toolbar_tv_title.setText("Select Address");
        toolbar_menu_iv.setVisibility(View.GONE);
        toolbar_logo_iv.setVisibility(View.GONE);
        toolbar_back_iv.setVisibility(View.VISIBLE);
        toolbar_tv_title.setVisibility(View.VISIBLE);
        toolbar_lay_cart = (RelativeLayout) findViewById(R.id.toolbar_lay_cart);
        toolbar_lay_cart.setVisibility(View.GONE);
        //toolbar
        s_addr_tv_agree = (TextView) findViewById(R.id.s_addr_tv_agree);
        String first = "I agree to ";
        String last = " Privacy and Policy ";
        String mid = "<font color='#4285F4'>PDC</font> ";
        s_addr_tv_agree.setText(Html.fromHtml(first + mid + last));


        dropdown_state_spinner = (Spinner) findViewById(R.id.dropdown_state_spinner);

        dropdown_country_spinner = (Spinner) findViewById(R.id.dropdown_country_spinner);

        dropdown_city_spinner = (Spinner) findViewById(R.id.dropdown_city_spinner);

        rv_address_select = findViewById(R.id.rv_address_select);
        rv_address_select.setLayoutManager(new LinearLayoutManager(this));
        addressSelectAdapter = new AddressSelectAdapter(addressList, this);
        rv_address_select.setAdapter(addressSelectAdapter);

        rb_addre_new = findViewById(R.id.rb_addre_new);

        et_addnew_altername_ph = findViewById(R.id.et_addnew_altername_ph);
        et_add_newaddre_ph = findViewById(R.id.et_add_newaddre_ph);
        et_addnew_addre_addre = findViewById(R.id.et_addnew_addre_addre);
        //et_addnew_city = findViewById(R.id.et_addnew_city);
        et_addnew_land = findViewById(R.id.et_addnew_land);
        et_addnew_str = findViewById(R.id.et_addnew_str);
        et_add_newaddre_name = findViewById(R.id.et_add_newaddre_name);
        lay_addre_cancel = findViewById(R.id.lay_addre_cancel);
        lay_addre_savedelivery = findViewById(R.id.lay_addre_savedelivery);
        tv_addr_addrss_default = findViewById(R.id.tv_addr_addrss_default);
        tv_addr_pincode = findViewById(R.id.tv_addr_pincode);
        lay_add_new_address = findViewById(R.id.lay_add_new_address);
        lay_add_new_address.setVisibility(View.GONE);

        lay_addre_savedelivery.setOnClickListener(onClickListener);
        lay_addre_cancel.setOnClickListener(onClickListener);
        tv_address_title.setOnClickListener(onClickListener);
        toolbar_back_iv.setOnClickListener(onClickListener);
        rb_addre_new.setOnCheckedChangeListener(onCheckedChangeListener);


    }

   /* @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (dropdown_country_spinner.hashCode() == adapterView.hashCode()) {
            countryId = idCountriesList.get(i);
            Log.d(TAG, "cId" + countryId);
        } else if (dropdown_state_spinner.hashCode() == adapterView.hashCode()) {
            state = adapterView.getSelectedItem().toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }*/

    public void rbListener() {
        onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                /*if (rb_addre_default.hashCode() == compoundButton.hashCode()){

                    if (rb_addre_default.isChecked()){
                        rb_addre_new.setChecked(false);
                        lay_add_new_address.setVisibility(View.GONE);
                    }
                }else if (rb_addre_new.hashCode() == compoundButton.hashCode()){

                    if (rb_addre_new.isChecked()){
                        rb_addre_default.setChecked(false);
                        lay_add_new_address.setVisibility(View.VISIBLE);
                    }

                }*/

                if (rb_addre_new.isChecked()) {
                    addressSelectAdapter.addressSelectStatus(false);
                    lay_add_new_address.setVisibility(View.VISIBLE);
                }
            }
        };
    }

    private void onClick() {
        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lay_addre_savedelivery.hashCode() == view.hashCode()) {
                    if (rb_addre_new.isChecked()) {
                        addressDefault = -1;
                    }

                    if (addressDefault >= 0) {

                        String addre = addressList.get(addressDefault).getFirstname();
                        String pincode = addressList.get(addressDefault).getPostcode();

                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("custId", customerID);
                        jsonObject.addProperty("firstname", addre);
                        jsonObject.addProperty("streetname", addre);
                        jsonObject.addProperty("countryId", 1);
                        jsonObject.addProperty("regionId", 1);
                        jsonObject.addProperty("areaId", 1);
                        jsonObject.addProperty("postcode", pincode);
                        Log.w(TAG, "onClick: default address " + jsonObject);
                        api.addressAdd(jsonObject);

                    } else if (rb_addre_new.isChecked()) {

                        String name = et_add_newaddre_name.getText().toString();
                        String ph = et_add_newaddre_ph.getText().toString();
                        String address = et_addnew_addre_addre.getText().toString();
                        String street = et_addnew_str.getText().toString();
                        //String city = et_addnew_city.getText().toString();
                        String landmark = et_addnew_land.getText().toString();
                        String alter_ph = et_addnew_altername_ph.getText().toString();

                        JsonObject jsonObject = new JsonObject();
                        if (tv_address_title.getText().equals("Edit Address")) {
                            jsonObject.addProperty("addressId", addressId);
                        }


                        jsonObject.addProperty("customerId", customerID);
                        jsonObject.addProperty("firstname", name);
                        jsonObject.addProperty("streetname", street);
                        jsonObject.addProperty("countryId", 1);
                        jsonObject.addProperty("regionId", 1);
                        jsonObject.addProperty("areaId", 1);
                        jsonObject.addProperty("postcode", 1);
                        Log.w(TAG, "onClick: add addredd " + jsonObject);
                        api.addressAdd(jsonObject);
                    } else {
                        Toast.makeText(SelectAddressActivity.this,
                                "Please select any one address ",
                                Toast.LENGTH_LONG).show();
                    }

                } else if (toolbar_back_iv.hashCode() == view.hashCode()) {
                    finish();
                } else if (lay_addre_cancel.hashCode() == view.hashCode()) {
                    finish();
                }

            }
        };
    }

    public void apiResponseAddAdress(AddressModel addressModel) {
        if (addressModel != null) {
            int addressId = addressModel.getId();
            String addressName = addressModel.getFirstname();
            String addStreetName = addressModel.getStreetname();
            Log.w(TAG, "apiResponseAddAdress: addressId " + addressId);
            Log.w(TAG, "apiResponseAddAdress: addressName " + addressName);
            Log.w(TAG, "apiResponseAddAdress: addStreetName " + addStreetName);
            preferencesHelper.setAddressId(addressId);
            finish();
        }


        /*Intent intent = new Intent(SelectAddressActivity.this,PaymentActivity.class);
        startActivity(intent);*/

    }

    public void apiResponseAdressList(List<AddressSingleUserModel> apiDataAddreList) {
        if (apiDataAddreList != null) {
            addressList = apiDataAddreList;
            if (addressList.size() > 0) {
                rv_address_select.setVisibility(View.VISIBLE);
                addressSelectAdapter.updateAddresAdapter(addressList);
            } else {
                Log.d(TAG, "apiResponseAdressList: address list " + addressList.size());
                rv_address_select.setVisibility(View.GONE);
            }

        } else {
            rv_address_select.setVisibility(View.GONE);
        }

    }

    public void raddioStatusUpdate(int pos) {
        addressDefault = pos;
        Log.w(TAG, "raddioStatusUpdate: pos " + pos);
        if (addressDefault >= 0) {
            rb_addre_new.setChecked(false);
            tv_address_title.setText("Add New Address");
            et_addnew_str.setText("");
            et_addnew_addre_addre.setText("");
            et_add_newaddre_name.setText("");
        }
    }

    public void addressEdit(int pos) {
        addressDefault = -1;
        rb_addre_new.setChecked(true);
        tv_address_title.setText("Edit Address");
        et_addnew_str.setText("");
        et_addnew_addre_addre.setText("");
        et_add_newaddre_name.setText("");
        addressId = addressList.get(pos).getAddressId();
        Log.w(TAG, "addressEdit: addressId " + addressId);
        String firstName = addressList.get(pos).getFirstname();
        String address = addressList.get(pos).getStreetname();
        String streetName = addressList.get(pos).getStreetname();
        String cityName = addressList.get(pos).getAreaId();
        et_addnew_str.setText(streetName);
        et_addnew_addre_addre.setText(address);
        et_add_newaddre_name.setText(firstName);
    }

    public void getCountries() {
        //utils.progressShow();
        countriesList.clear();
        idCountriesList.clear();
        Call<List<GetCountries>> call = apiInterface.getAllCountries();
        call.enqueue(new Callback<List<GetCountries>>() {
            @Override
            public void onResponse(Call<List<GetCountries>> call, Response<List<GetCountries>> response) {
                //utils.progressHide();
                List<GetCountries> countries = response.body();
                if (countries != null) {
                    Log.e(TAG, "onResponse:  AddressListModel " + countries);

                    for (GetCountries country : countries) {
                        countriesList.add(country.getName());
                        idCountriesList.add("" + country.getId());
                    }

                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(SelectAddressActivity.this,
                            android.R.layout.simple_spinner_item, countriesList);

                    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dropdown_country_spinner.setAdapter(adapter1);

                } else {
                    //utils.showMessage(context.getResources().getString(R.string.no_response), (Activity) context);
                    Toast.makeText(SelectAddressActivity.this, "response null", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<GetCountries>> call, Throwable t) {

            }

        });


    }

    public void getStates(String countryId) {
        //utils.progressShow();
        statesList.clear();
        idStatesList.clear();
        Call<List<GetStates>> call = apiInterface.getAllStates(countryId);
        call.enqueue(new Callback<List<GetStates>>() {
            @Override
            public void onResponse(Call<List<GetStates>> call, Response<List<GetStates>> response) {
                //utils.progressHide();
                List<GetStates> states = response.body();
                if (states != null) {
                    Log.e(TAG, "onResponse:  StatesModel " + states);

                    for (GetStates state : states) {
                        statesList.add(state.getName());
                        idStatesList.add("" + state.getId());
                    }

                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(SelectAddressActivity.this,
                            android.R.layout.simple_spinner_item, statesList);

                    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dropdown_state_spinner.setAdapter(adapter1);

                } else {
                    //utils.showMessage(context.getResources().getString(R.string.no_response), (Activity) context);
                    Toast.makeText(SelectAddressActivity.this, "response null", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<GetStates>> call, Throwable t) {

            }

        });


    }
    public void getCity(String countryId,String stateId) {
        //utils.progressShow();
        citiesList.clear();
        idCitiesList.clear();
        Call<List<GetCity>> call = apiInterface.getAllCities(countryId,stateId);
        call.enqueue(new Callback<List<GetCity>>() {
            @Override
            public void onResponse(Call<List<GetCity>> call, Response<List<GetCity>> response) {
                //utils.progressHide();
                List<GetCity> cities = response.body();
                if (cities != null) {
                    Log.e(TAG, "onResponse:  cityListModel " + cities);

                    for (GetCity city : cities) {
                        citiesList.add(city.getName());
                        idCitiesList.add("" + city.getId());
                    }

                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(SelectAddressActivity.this,
                            android.R.layout.simple_spinner_item, citiesList);

                    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dropdown_city_spinner.setAdapter(adapter1);

                } else {
                    //utils.showMessage(context.getResources().getString(R.string.no_response), (Activity) context);
                    Toast.makeText(SelectAddressActivity.this, "response null", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<GetCity>> call, Throwable t) {

            }

        });


    }


}
