package me.kimhieu.yummy.ecommerceproject.login;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.kimhieu.yummy.ecommerceproject.R;
import me.kimhieu.yummy.ecommerceproject.model.APIErrorsResponse;
import me.kimhieu.yummy.ecommerceproject.model.Customer;
import me.kimhieu.yummy.ecommerceproject.model.CustomerResponse;
import me.kimhieu.yummy.ecommerceproject.service.ServiceGenerator;
import me.kimhieu.yummy.ecommerceproject.service.WooCommerceService;
import me.kimhieu.yummy.ecommerceproject.utils.ErrorUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;

    private TextView textViewEmail;
    private TextView textViewUserName;
    private TextView textViewPassword;
    private ImageButton imageButtonSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        viewPager = (ViewPager) findViewById(R.id.view_pager_sign_up);
        textViewEmail = (TextView) findViewById(R.id.text_view_sign_up_email);
        textViewUserName = (TextView) findViewById(R.id.text_view_sign_up_user_name);
        textViewPassword = (TextView) findViewById(R.id.text_view_sign_up_password);
        imageButtonSignUp = (ImageButton) findViewById(R.id.image_button_sign_up);

        List<Fragment> fragmentList = getFragment();
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(pagerAdapter);

        imageButtonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Customer customer = new Customer();
                customer.setEmail(textViewEmail.getText().toString());
                customer.setUsername(textViewUserName.getText().toString());
                customer.setPassword(textViewPassword.getText().toString());
                WooCommerceService service = ServiceGenerator.createService(WooCommerceService.class);
                Call<CustomerResponse> responseBodyCall = service.addCustomer(new CustomerResponse(customer));
                responseBodyCall.enqueue(new Callback<CustomerResponse>() {
                    @Override
                    public void onResponse(Call<CustomerResponse> call, Response<CustomerResponse> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this,
                                    "Your account has been created.\nPlease login to continue",
                                    Toast.LENGTH_SHORT).show();
                            SignUpActivity.this.finish();
                        }else {
                            APIErrorsResponse errorsResponse = ErrorUtils.parseError(response);
                            Log.d("error message", errorsResponse.getErrors().get(0).getMessage());
                            Toast.makeText(SignUpActivity.this,
                                    errorsResponse.getErrors().get(0).getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CustomerResponse> call, Throwable t) {
                        Toast.makeText(SignUpActivity.this,
                                "Failed to create account.\nPlease try again",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    public List<Fragment> getFragment() {
        List<Fragment> fragments = new ArrayList<Fragment>();

        fragments.add(PageFragment.createInstance(R.drawable.slider));
        fragments.add(PageFragment.createInstance(R.drawable.slider));
        fragments.add(PageFragment.createInstance(R.drawable.slider));

        return fragments;
    }

    public void clearText() {
        textViewUserName.setText(null);
        textViewEmail.setText(null);
        textViewPassword.setText(null);
    }
}
