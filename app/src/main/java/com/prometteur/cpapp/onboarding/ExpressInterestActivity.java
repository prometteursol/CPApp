package com.prometteur.cpapp.onboarding;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.androidbuts.multispinnerfilter.SingleSpinnerListener;
import com.androidbuts.multispinnerfilter.SingleSpinnerSearch;
import com.androidbuts.multispinnerfilter.SingleSpinnerListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.prometteur.cpapp.R;
import com.prometteur.cpapp.beans.ExpressInterestBean;
import com.prometteur.cpapp.beans.LoginBean;
import com.prometteur.cpapp.dialogs.SuccessDialogActivity;
import com.prometteur.cpapp.models.StateCityBean;
import com.prometteur.cpapp.models.StateCityModel;
import com.prometteur.cpapp.models.onboarding.ActiveInactiveBean;
import com.prometteur.cpapp.models.onboarding.SurveyDetail;
import com.prometteur.cpapp.retrofit.ApiInterface;
import com.prometteur.cpapp.retrofit.RetrofitInstance;
import com.prometteur.cpapp.utils.GPSTracker;
import com.prometteur.cpapp.utils.ImagePickerActivity;
import com.prometteur.cpapp.utils.Utils;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;

import static com.prometteur.cpapp.onboarding.ChangePasswordActivity.resultCodeChangePass;
import static com.prometteur.cpapp.utils.Utils.getImageBody;
import static com.prometteur.cpapp.utils.Utils.getOtherFields;
import static com.prometteur.cpapp.utils.Utils.isNetworkAvailable;
import static com.prometteur.cpapp.utils.Utils.showFailToast;
import static com.prometteur.cpapp.utils.Utils.showNoInternetDialog;
import static com.prometteur.cpapp.utils.Utils.showProgress;

public class ExpressInterestActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private static final String EMAIL = "email";
    /*Google Signup*/
    private static final String TAG = "GoogleSignInActivity";
    private static final int RC_SIGN_IN = 9001;
    public static int REQUEST_IMAGE = 121;
    protected String latitude, longitude;
    TextView tvEstYear, tvFromTime, tvToTime;
    Button btnSave;
    LinearLayout linHairBlock, linSkinBlock, linNailsBlock, linSpaBlock, linMakeupBlock, linOtherServiceBlockContainer, linGstContainer;
    RadioGroup rgGst, rgCat;
    RadioButton rbYes, rbNo;
    SingleSpinnerSearch spState, spCity;
    Spinner spGstInPer, spMainCategory;
    boolean clearData = false;
    //Block hide and show
    CheckBox tvSalonDetails, tvSocialMediaLbl, tvOwnerDetails, tvSerOffDetails, tvInfraStDetails;
    CardView cvSecond, cvSocMed, cvFourth, cvEighth, cvTenth;
    //time picker
    int mHour, mMinute;
    //add other services
    ImageView ivAddView;
    //states and city json
    List<StateCityModel> stateCityModels;
    StateCityBean stateCityBean = null;
    //input fields
    EditText edtSalonName, edtManName, edtAddress, edtPinCode, edtContNumber, edtManContNumber,
            edtGstNo, edtWebsite, edtEmailId, edtFacebook, edtInstagram, edtOwnerName,
            edtOwnContNumber, edtOwnEdu,
            edtNoOfOptHair, edtHairOtherBrand,
            edtNoOfOptSkin, edtSkinOtherBrand,
            edtNoOfOptNails, edtNailsOtherBrand,
            edtNoOfOptSpa, edtSpaOtherBrand,
            edtNoOfOptMakeup, edtMakeupOtherBrand;
    TextInputLayout tilSalonName, tilManName, tilManContNumber, tilGstNo, tilAddress, tilInstitueCert, tilPinCode, tilContactNumber, tilWebsite, tilEmailId, tilOwnName, tilOwnContNumber, tilNoOfHouseKeepStaff, tilNoOfMgmtStaff, tilNoOfSkinChair;
    //checkboxes brands
    CheckBox hairBrand1, hairBrand2, hairBrand3, hairBrand4, hairBrand5, hairBrand6, hairBrand7, hairBrand8, hairBrand9, hairBrand10;
    CheckBox skinBrand1, skinBrand2, skinBrand3, skinBrand4, skinBrand5, skinBrand6, skinBrand7, skinBrand8, skinBrand9, skinBrand10,
            skinBrand11, skinBrand12, skinBrand13, skinBrand14, skinBrand15;
    CheckBox nailsBrand1, nailsBrand2, nailsBrand3, nailsBrand4, nailsBrand5;
    CheckBox spaBrand1, spaBrand2;
    CheckBox makeupBrand1, makeupBrand2, makeupBrand3, makeupBrand4, makeupBrand5, makeupBrand6;
    Calendar calendar = Calendar.getInstance();
    int choosenYear = calendar.get(Calendar.YEAR);
    int currentYear = calendar.get(Calendar.YEAR);
    Set<String> hairSet = new HashSet<>();
    Set<String> skinSet = new HashSet<>();
    Set<String> nailsSet = new HashSet<>();
    Set<String> spaSet = new HashSet<>();
    Set<String> makeupSet = new HashSet<>();
    List<String> imgArr = new ArrayList<>();
    //radiogroup infra
    RadioGroup rgRP, rgTV, rgWF, rgAc;
    EditText noOfHKStaff, noOfMgmtStaff, noOfHairSkinChairs;
    String strRbRP, strRbTV, strRbWF, strRbAc,
            strNoOfHKStaff, strNoOfMgmtStaff, strNoOfHairSkinChairs;
    //get lat long
    LocationManager locationManager;
    GPSTracker gps;
    //db insert
    SurveyDetail addSalonBean;
    JSONArray othServiceData = new JSONArray();
    //edit
    SurveyDetail surveyDetail;
    RadioButton rbSalonCat, rbInstitueCat, rpYes, rpNo, tvYes, tvNo, wfYes, wfNo, acYes, acNo;
    LinearLayout insertPoint;
    ActiveInactiveBean activeInactiveBean;
    boolean b = true;
    String userId = "0"; //TODO for design
    TextView tvLogin,tvTermAndCondition;
    ExpressInterestBean loginBean;
    private CheckBox cbIsHair, cbIsSkin, cbIsNails, cbIsSpa, cbIsMakeup, cbIsOtherService;
    private CheckBox cbIsHairCheck, cbIsSkinCheck, cbIsNailsCheck, cbIsSpaCheck, cbIsMakeupCheck, cbIsOtherServiceCheck;
    private EditText edtInstitueCert;
    private String strEdtSalonName, strEdtManName, strEdtAddress, strState = "", strCity = "",
            strEdtPinCode, strTvEstYear, strTvFromTime, strTvToTime, strEdtContNumber,
            strEdtManContNumber, strGstYesNo = "", strEdtGstNo, strGstRate = "", strEdtInstitueCert,
            strEdtWebsite, strEdtEmailId, strEdtFacebook, strEdtInstagram, strEdtOwnerName, strEdtOwnContNumber,
            strEdtOwnEdu, strEdtNoOfOptHair, strEdtHairOtherBrand,
            strEdtNoOfOptSkin, strEdtSkinOtherBrand,
            strEdtNoOfOptNails, strEdtNailsOtherBrand,
            strEdtNoOfOptSpa, strEdtSpaOtherBrand,
            strEdtNoOfOptMakeup, strEdtMakeupOtherBrand,
            strImgPaths, strOtherServicesArrayJson, strStartTime, strEndTime, strCatMain = "";

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_express_interest);
        int currentapiVersion = Build.VERSION.SDK_INT;
        if (Build.VERSION_CODES.P == currentapiVersion) {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        }


        if (getIntent().getStringExtra("editMode").equalsIgnoreCase("edit")) {
            setTitle("Edit Survey");
           /* RealmResults<SurveyDetail> result = MyApp.realm.where(SurveyDetail.class).equalTo("survLocalId", getIntent().getIntExtra("surveyLocalId", 0)).findAll();
            surveyDetail=new SurveyDetail();
            surveyDetail = result.get(0);*/
            //TODO for design
        } else {
            setTitle("Add Survey");
        }
//permission for read file and write file
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        //squre removed
        tvLogin = findViewById(R.id.tvLogin);
        tvTermAndCondition = findViewById(R.id.tvTermAndCondition);


        cbIsHair = findViewById(R.id.cbIsHair);
        cbIsSkin = findViewById(R.id.cbIsSkin);
        cbIsNails = findViewById(R.id.cbIsNails);
        cbIsSpa = findViewById(R.id.cbIsSpa);
        cbIsMakeup = findViewById(R.id.cbIsMakeup);
        cbIsOtherService = findViewById(R.id.cbIsOtherService);

        cbIsHairCheck = findViewById(R.id.cbIsHairCheck);
        cbIsSkinCheck = findViewById(R.id.cbIsSkinCheck);
        cbIsNailsCheck = findViewById(R.id.cbIsNailsCheck);
        cbIsSpaCheck = findViewById(R.id.cbIsSpaCheck);
        cbIsMakeupCheck = findViewById(R.id.cbIsMakeupCheck);
        cbIsOtherServiceCheck = findViewById(R.id.cbIsOtherServiceCheck);

        linHairBlock = findViewById(R.id.linHairBlock);
        linSkinBlock = findViewById(R.id.linSkinBlock);
        linNailsBlock = findViewById(R.id.linNailsBlock);
        linSpaBlock = findViewById(R.id.linSpaBlock);
        linMakeupBlock = findViewById(R.id.linMakeupBlock);
        linOtherServiceBlockContainer = findViewById(R.id.linOtherServiceBlockContainer);
        linGstContainer = findViewById(R.id.linGstContainer);


        tvEstYear = findViewById(R.id.tvEstYear);
        tvFromTime = findViewById(R.id.tvFromTime);
        tvToTime = findViewById(R.id.tvToTime);

        //radiobuttons
        /*rbSalonCat = findViewById(R.id.rbSalonCat);
        rbInstitueCat = findViewById(R.id.rbInstitueCat);*/

        rpYes = findViewById(R.id.rpYes);
        rpNo = findViewById(R.id.rpNo);
        tvYes = findViewById(R.id.tvYes);
        tvNo = findViewById(R.id.tvNo);
        wfYes = findViewById(R.id.wfYes);
        wfNo = findViewById(R.id.wfNo);
        acYes = findViewById(R.id.acYes);
        acNo = findViewById(R.id.acNo);

        //main cat selection
        edtInstitueCert = findViewById(R.id.edtInstitueCert);
//        rgCat = findViewById(R.id.rgCat);
        spMainCategory = findViewById(R.id.spMainCategory);
        //gst hide show
        rgGst = findViewById(R.id.rgGst);
        rbYes = findViewById(R.id.rbYes);
        rbNo = findViewById(R.id.rbNo);
        spGstInPer = findViewById(R.id.spGstInPer);
        //city and state
        /*spState = findViewById(R.id.spState);
        spCity = findViewById(R.id.spCity);
*/
        spState = (SingleSpinnerSearch) findViewById(R.id.spState);
        spCity = (SingleSpinnerSearch) findViewById(R.id.spCity);
        setSpinner();
        //tvState = findViewById(R.id.tvState);
        //tvCity = findViewById(R.id.tvCity);

        ivAddView = findViewById(R.id.ivAddView);


        //hide show block/section
        tvSalonDetails = findViewById(R.id.tvSalonDetails);
        tvSocialMediaLbl = findViewById(R.id.tvSocialMediaLbl);
        tvOwnerDetails = findViewById(R.id.tvOwnerDetails);
        tvSerOffDetails = findViewById(R.id.tvSerOffDetails);
        tvInfraStDetails = findViewById(R.id.tvInfraStDetails);

        cvSecond = findViewById(R.id.cvSecond);
        cvSocMed = findViewById(R.id.cvSocMed);
        cvFourth = findViewById(R.id.cvFourth);
        cvEighth = findViewById(R.id.cvEighth);
        cvTenth = findViewById(R.id.cvTenth);


//input fields init
        edtSalonName = findViewById(R.id.edtSalonName);
        edtManName = findViewById(R.id.edtManName);
        edtAddress = findViewById(R.id.edtAddress);
        edtPinCode = findViewById(R.id.edtPinCode);
        edtContNumber = findViewById(R.id.edtContNumber);
        edtManContNumber = findViewById(R.id.edtManContNumber);
        edtGstNo = findViewById(R.id.edtGstNo);
        edtWebsite = findViewById(R.id.edtWebsite);
        edtEmailId = findViewById(R.id.edtEmailId);
        edtFacebook = findViewById(R.id.edtFacebook);
        edtInstagram = findViewById(R.id.edtInstagram);
        edtOwnerName = findViewById(R.id.edtOwnerName);
        edtOwnContNumber = findViewById(R.id.edtOwnContNumber);
        edtOwnEdu = findViewById(R.id.edtOwnEdu);

        edtNoOfOptHair = findViewById(R.id.edtNoOfOptHair);
        edtNoOfOptSkin = findViewById(R.id.edtNoOfOptSkin);
        edtNoOfOptNails = findViewById(R.id.edtNoOfOptNails);
        edtNoOfOptSpa = findViewById(R.id.edtNoOfOptSpa);
        edtNoOfOptMakeup = findViewById(R.id.edtNoOfOptMakeup);

        edtHairOtherBrand = findViewById(R.id.edtHairOtherBrand);
        edtSkinOtherBrand = findViewById(R.id.edtSkinOtherBrand);
        edtNailsOtherBrand = findViewById(R.id.edtNailsOtherBrands);
        edtSpaOtherBrand = findViewById(R.id.edtSpaOtherBrand);
        edtMakeupOtherBrand = findViewById(R.id.edtMakeupOtherBrand);

        noOfHKStaff = findViewById(R.id.noOfHKStaff);
        noOfMgmtStaff = findViewById(R.id.noOfMgmtStaff);
        noOfHairSkinChairs = findViewById(R.id.noOfHairSkinChairs);

        //input layout
        tilSalonName = findViewById(R.id.tilSalonName);
        tilManName = findViewById(R.id.tilManName);
        tilManContNumber = findViewById(R.id.tilManContNumber);
        tilGstNo = findViewById(R.id.tilGstNo);
        tilAddress = findViewById(R.id.tilAddress);
        tilPinCode = findViewById(R.id.tilPinCode);
        tilContactNumber = findViewById(R.id.tilContactNumber);
        tilWebsite = findViewById(R.id.tilWebsite);
        tilEmailId = findViewById(R.id.tilEmailId);
        tilOwnName = findViewById(R.id.tilOwnName);
        tilOwnContNumber = findViewById(R.id.tilOwnContNumber);
        tilNoOfHouseKeepStaff = findViewById(R.id.tilNoOfHouseKeepStaff);
        tilNoOfMgmtStaff = findViewById(R.id.tilNoOfMgmtStaff);
        tilNoOfSkinChair = findViewById(R.id.tilNoOfSkinChair);
        tilInstitueCert = findViewById(R.id.tilInstitueCert);

        setInputLayout(tilSalonName, edtSalonName);
        setInputLayout(tilManName, edtManName);
        setInputLayout(tilManContNumber, edtOwnContNumber);
        setInputLayout(tilGstNo, edtGstNo);
        setInputLayout(tilAddress, edtAddress);
        setInputLayout(tilPinCode, edtPinCode);
        setInputLayout(tilContactNumber, edtContNumber);
        setInputLayout(tilWebsite, edtWebsite);
        setInputLayout(tilEmailId, edtEmailId);
        setInputLayout(tilOwnName, edtOwnerName);
        setInputLayout(tilOwnContNumber, edtOwnContNumber);
        setInputLayout(tilNoOfHouseKeepStaff, noOfHKStaff);
        setInputLayout(tilNoOfMgmtStaff, noOfMgmtStaff);
        setInputLayout(tilNoOfSkinChair, noOfHairSkinChairs);
        setInputLayout(tilInstitueCert, edtInstitueCert);

        setCheckBoxInit();
//infrastructure details init
        rgRP = findViewById(R.id.rgRP);
        rgTV = findViewById(R.id.rgTV);
        rgWF = findViewById(R.id.rgWF);
        rgAc = findViewById(R.id.rgAc);


        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable(ExpressInterestActivity.this)) {
                    saveDataInLocalBD();
                } else {
                    // Toast.makeText(LoginActivity.this, getResources().getString(R.string.please_check_newtork_connection), Toast.LENGTH_SHORT).show();

                    showNoInternetDialog(ExpressInterestActivity.this);
                }


                /*startActivity(new Intent(ExpressInterestActivity.this, PreviewSurveyDetailActivity.class));
                finish();*/
            }

        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ExpressInterestActivity.this, LoginActivity.class));
            }
        });
        tvTermAndCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebPage("https://mooi.app/our-policy/Mooi_ChannelPartner_TermsAndConditions.html");
            }
        });
        if (getIntent().getStringExtra("editMode").equalsIgnoreCase("edit")) {
            String img = surveyDetail.getSurvImg();

            if (img != null && !img.isEmpty()) {

                String[] imgArr1 = img.split(",");
                for (int i = 0; i < imgArr1.length; i++) {
                    LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View v = vi.inflate(R.layout.image_picker_layout, null);
                    LinearLayout insertPoint = (LinearLayout) findViewById(R.id.imageContainer);
                    ImageView salonImg = (ImageView) v.findViewById(R.id.salonImg);
                    insertPoint.addView(v);

                    imgArr.add(imgArr1[i]);
                    Bitmap bitmap = getBitmap(imgArr1[i]);
                    if (bitmap != null) {
                        salonImg.setImageBitmap(bitmap);
                    }
                }

            }
            addView();


            removeDrawableCheckbox(R.drawable.ic_keyboard_arrow_up_black_24dp, cbIsHair);
            removeDrawableCheckbox(R.drawable.ic_keyboard_arrow_up_black_24dp, cbIsSkin);
            removeDrawableCheckbox(R.drawable.ic_keyboard_arrow_up_black_24dp, cbIsNails);
            removeDrawableCheckbox(R.drawable.ic_keyboard_arrow_up_black_24dp, cbIsSpa);
            removeDrawableCheckbox(R.drawable.ic_keyboard_arrow_up_black_24dp, cbIsMakeup);
            removeDrawableCheckbox(R.drawable.ic_keyboard_arrow_up_black_24dp, cbIsOtherService);
        } else {
            addView();
        }
        linHairBlock.setVisibility(View.GONE);
        linSkinBlock.setVisibility(View.GONE);
        linNailsBlock.setVisibility(View.GONE);
        linSpaBlock.setVisibility(View.GONE);
        linMakeupBlock.setVisibility(View.GONE);
        linOtherServiceBlockContainer.setVisibility(View.GONE);
        //showing edit values
        //insertPoint = findViewById(R.id.linOtherServiceBlockContainer);
        if (getIntent().getStringExtra("editMode").equalsIgnoreCase("edit")) {
            strCatMain = surveyDetail.getSurvCatMain();
            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray(surveyDetail.getSurvOtrService());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (jsonArray != null) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.add_other_services_layout, null, false);
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = jsonArray.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    // insert into main view
                    LinearLayout insertPoint = findViewById(R.id.linOtherServiceBlockContainer);
                    EditText otherServiceName = v.findViewById(R.id.otherServiceName);
                    EditText otherServiceNoofOpt = v.findViewById(R.id.otherServiceNoofOpt);
                    EditText otherSerOtherBrand = v.findViewById(R.id.otherSerOtherBrand);
                    try {
                        otherServiceName.setText(jsonObject.getString("otherServiceName"));
                        otherServiceNoofOpt.setText(jsonObject.getString("otherServiceNoofOpt"));
                        otherSerOtherBrand.setText(jsonObject.getString("otherSerOtherBrand"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (surveyDetail.getSurvCatMain().equalsIgnoreCase("Institute")) {
                        otherServiceNoofOpt.setVisibility(View.GONE);
                        otherSerOtherBrand.setVisibility(View.GONE);
                        cbIsOtherServiceCheck.setChecked(true);
                        //cbIsOtherService.performClick();

                    }


                    insertPoint.addView(v);
                    insertPoint.setVisibility(View.VISIBLE);
                }

            }
            addViewOtherServices();
                /*ivAddView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        addViewOtherServices();
                    }
                });*/

        } else {
            addViewOtherServices();
            /*ivAddView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    addViewOtherServices();
                }
            });*/
        }

        //ivAddView.setVisibility(View.GONE);

        cvSecond.setVisibility(View.GONE);
        cvSocMed.setVisibility(View.GONE);
        cvFourth.setVisibility(View.GONE);
        cvEighth.setVisibility(View.GONE);
        cvTenth.setVisibility(View.GONE);

        cbIsHair.setOnCheckedChangeListener(this);
        cbIsSkin.setOnCheckedChangeListener(this);
        cbIsNails.setOnCheckedChangeListener(this);
        cbIsSpa.setOnCheckedChangeListener(this);
        cbIsMakeup.setOnCheckedChangeListener(this);
        cbIsOtherService.setOnCheckedChangeListener(this);

        cbIsHairCheck.setOnCheckedChangeListener(this);
        cbIsSkinCheck.setOnCheckedChangeListener(this);
        cbIsNailsCheck.setOnCheckedChangeListener(this);
        cbIsSpaCheck.setOnCheckedChangeListener(this);
        cbIsMakeupCheck.setOnCheckedChangeListener(this);
        cbIsOtherServiceCheck.setOnCheckedChangeListener(this);

        tvSalonDetails.setOnCheckedChangeListener(this);
        tvSocialMediaLbl.setOnCheckedChangeListener(this);
        tvOwnerDetails.setOnCheckedChangeListener(this);
        tvSerOffDetails.setOnCheckedChangeListener(this);
        tvInfraStDetails.setOnCheckedChangeListener(this);

        tvEstYear.setOnClickListener(this);
        tvToTime.setOnClickListener(this);
        tvFromTime.setOnClickListener(this);


        rgGst.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbYes) {
                    strGstYesNo = "Yes";
                    linGstContainer.setVisibility(View.VISIBLE);

                } else {
                    strGstYesNo = "No";
                    linGstContainer.setVisibility(View.GONE);

                }
            }
        });
        spMainCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (clearData) {
                    clearServiceData();  //service data cleared "Service Offered"
                    LinearLayout insertPoint = findViewById(R.id.linOtherServiceBlockContainer); //remove added views
                    if (insertPoint != null) {
                        insertPoint.removeAllViews();
                    }
                    //addViewOtherServices();
                }
                if (!clearData) {
                    clearData = true;
                }
                if (position == 0) {
                } else {
                    if (spMainCategory.getSelectedItem().toString().equalsIgnoreCase("Institute")) {
                        edtInstitueCert.setVisibility(View.VISIBLE);
                        strCatMain = spMainCategory.getSelectedItem().toString();
                        tvSalonDetails.setText("Institute Details");
                        tilSalonName.setHint("Institute Name*");

                        removeDrawableCheckbox(R.drawable.ic_keyboard_arrow_up_black_24dp, cbIsHair);
                        removeDrawableCheckbox(R.drawable.ic_keyboard_arrow_up_black_24dp, cbIsSkin);
                        removeDrawableCheckbox(R.drawable.ic_keyboard_arrow_up_black_24dp, cbIsNails);
                        removeDrawableCheckbox(R.drawable.ic_keyboard_arrow_up_black_24dp, cbIsSpa);
                        removeDrawableCheckbox(R.drawable.ic_keyboard_arrow_up_black_24dp, cbIsMakeup);
                        removeDrawableCheckbox(R.drawable.ic_keyboard_arrow_up_black_24dp, cbIsOtherService);
                    } else {
                        edtInstitueCert.setVisibility(View.GONE);
                        strCatMain = spMainCategory.getSelectedItem().toString();
                        tvSalonDetails.setText("Salon Details");
                        tilSalonName.setHint("Salon Name*");

                        setDrawableCheckbox(R.drawable.ic_keyboard_arrow_down_black_24dp, cbIsHair);
                        setDrawableCheckbox(R.drawable.ic_keyboard_arrow_down_black_24dp, cbIsSkin);
                        setDrawableCheckbox(R.drawable.ic_keyboard_arrow_down_black_24dp, cbIsNails);
                        setDrawableCheckbox(R.drawable.ic_keyboard_arrow_down_black_24dp, cbIsSpa);
                        setDrawableCheckbox(R.drawable.ic_keyboard_arrow_down_black_24dp, cbIsMakeup);
                        setDrawableCheckbox(R.drawable.ic_keyboard_arrow_down_black_24dp, cbIsOtherService);
                    }
                    addViewOtherServices();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /*rgCat.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbInstitueCat) {
                    edtInstitueCert.setVisibility(View.VISIBLE);
                    strCatMain = ((RadioButton) rgCat.findViewById(checkedId)).getText().toString();
                    tvSalonDetails.setText("Institute Details");
                    tilSalonName.setHint("Institute Name*");

                } else {
                    edtInstitueCert.setVisibility(View.GONE);
                    strCatMain = ((RadioButton) rgCat.findViewById(checkedId)).getText().toString();
                    tvSalonDetails.setText("Salon Details");
                    tilSalonName.setHint("Salon Name*");
                }
            }
        });*/


        spGstInPer.setAdapter(new ArrayAdapter<String>(ExpressInterestActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.gst_rate_array)));
        spGstInPer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                } else {
                    strGstRate = spGstInPer.getSelectedItem().toString();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //defalut value setting
        setDrawableTextView(R.drawable.ic_keyboard_arrow_down_black_24dp, tvSalonDetails);
        setDrawableTextView(R.drawable.ic_keyboard_arrow_down_black_24dp, tvSocialMediaLbl);
        setDrawableTextView(R.drawable.ic_keyboard_arrow_down_black_24dp, tvOwnerDetails);
        setDrawableTextView(R.drawable.ic_keyboard_arrow_down_black_24dp, tvSerOffDetails);
        setDrawableTextView(R.drawable.ic_keyboard_arrow_down_black_24dp, tvInfraStDetails);

        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        tvFromTime.setText("10:00");
        tvToTime.setText("20:00");

        setDrawableCheckbox(R.drawable.ic_keyboard_arrow_down_black_24dp, cbIsHair);
        setDrawableCheckbox(R.drawable.ic_keyboard_arrow_down_black_24dp, cbIsSkin);
        setDrawableCheckbox(R.drawable.ic_keyboard_arrow_down_black_24dp, cbIsNails);
        setDrawableCheckbox(R.drawable.ic_keyboard_arrow_down_black_24dp, cbIsSpa);
        setDrawableCheckbox(R.drawable.ic_keyboard_arrow_down_black_24dp, cbIsMakeup);
        setDrawableCheckbox(R.drawable.ic_keyboard_arrow_down_black_24dp, cbIsOtherService);

        if (getIntent().getStringExtra("editMode").equalsIgnoreCase("edit")) {
            if (surveyDetail.getSurvStartTime() != null && !surveyDetail.getSurvStartTime().isEmpty()) {
                strStartTime = surveyDetail.getSurvStartTime();
            } else {
                Calendar startEndTimeCal = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                strStartTime = simpleDateFormat.format(startEndTimeCal.getTimeInMillis());
            }
            setEditSalonData();

        } else {
            Calendar startEndTimeCal = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            strStartTime = simpleDateFormat.format(startEndTimeCal.getTimeInMillis());
        }
        //load state and cities
       /* spCity.setAdapter(new ArrayAdapter<String>(ExpressInterestActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.city_array)));
        loadJsonStateCity();*/


        gps = new GPSTracker(ExpressInterestActivity.this);

        // check if GPS enabled
        if (gps.canGetLocation()) {
            latitude = "" + gps.getLatitude();
            longitude = "" + gps.getLongitude();
        } else {
// can't get location
// GPS or Network is not enabled
// Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }


    }

    public void btnSave(View view) {
        startActivity(new Intent(this, OtpVerificationActivity.class));
    }
    public void openWebPage(String url) {
        try {
            Uri webpage = Uri.parse(url);
            Intent myIntent = new Intent(Intent.ACTION_VIEW, webpage);
            startActivity(myIntent);
        } catch (ActivityNotFoundException e) {
            showFailToast(this, "No application can handle this request. Please install a web browser or check your URL.");
            e.printStackTrace();
        }
    }
    private void setCheckBoxInit() {
        checkBoxInit(hairBrand1, R.id.hairBrand1, hairSet, null, "hair");
        checkBoxInit(hairBrand2, R.id.hairBrand2, hairSet, null, "hair");
        checkBoxInit(hairBrand3, R.id.hairBrand3, hairSet, null, "hair");
        checkBoxInit(hairBrand4, R.id.hairBrand4, hairSet, null, "hair");
        checkBoxInit(hairBrand5, R.id.hairBrand5, hairSet, null, "hair");
        checkBoxInit(hairBrand6, R.id.hairBrand6, hairSet, null, "hair");
        checkBoxInit(hairBrand7, R.id.hairBrand7, hairSet, null, "hair");
        checkBoxInit(hairBrand8, R.id.hairBrand8, hairSet, null, "hair");
        checkBoxInit(hairBrand9, R.id.hairBrand9, hairSet, null, "hair");
        checkBoxInit(hairBrand10, R.id.hairBrand10, null, edtHairOtherBrand, "hair");

        checkBoxInit(skinBrand1, R.id.skinBrand1, skinSet, null, "skin");
        checkBoxInit(skinBrand2, R.id.skinBrand2, skinSet, null, "skin");
        checkBoxInit(skinBrand3, R.id.skinBrand3, skinSet, null, "skin");
        checkBoxInit(skinBrand4, R.id.skinBrand4, skinSet, null, "skin");
        checkBoxInit(skinBrand5, R.id.skinBrand5, skinSet, null, "skin");
        checkBoxInit(skinBrand6, R.id.skinBrand6, skinSet, null, "skin");
        checkBoxInit(skinBrand7, R.id.skinBrand7, skinSet, null, "skin");
        checkBoxInit(skinBrand8, R.id.skinBrand8, skinSet, null, "skin");
        checkBoxInit(skinBrand9, R.id.skinBrand9, skinSet, null, "skin");
        checkBoxInit(skinBrand10, R.id.skinBrand10, skinSet, null, "skin");
        checkBoxInit(skinBrand11, R.id.skinBrand11, skinSet, null, "skin");
        checkBoxInit(skinBrand12, R.id.skinBrand12, skinSet, null, "skin");
        checkBoxInit(skinBrand13, R.id.skinBrand13, skinSet, null, "skin");
        checkBoxInit(skinBrand14, R.id.skinBrand14, skinSet, null, "skin");
        checkBoxInit(skinBrand15, R.id.skinBrand15, null, edtSkinOtherBrand, "skin");

        checkBoxInit(nailsBrand1, R.id.nailsBrand1, nailsSet, null, "nails");
        checkBoxInit(nailsBrand2, R.id.nailsBrand2, nailsSet, null, "nails");
        checkBoxInit(nailsBrand3, R.id.nailsBrand3, nailsSet, null, "nails");
        checkBoxInit(nailsBrand4, R.id.nailsBrand4, nailsSet, null, "nails");
        checkBoxInit(nailsBrand5, R.id.nailsBrand5, null, edtNailsOtherBrand, "nails");

        checkBoxInit(spaBrand1, R.id.spaBrand1, spaSet, null, "spa");
        checkBoxInit(spaBrand2, R.id.spaBrand2, null, edtSpaOtherBrand, "spa");

        checkBoxInit(makeupBrand1, R.id.makeupBrand1, makeupSet, null, "makeup");
        checkBoxInit(makeupBrand2, R.id.makeupBrand2, makeupSet, null, "makeup");
        checkBoxInit(makeupBrand3, R.id.makeupBrand3, makeupSet, null, "makeup");
        checkBoxInit(makeupBrand4, R.id.makeupBrand4, makeupSet, null, "makeup");
        checkBoxInit(makeupBrand5, R.id.makeupBrand5, makeupSet, null, "makeup");
        checkBoxInit(makeupBrand6, R.id.makeupBrand6, null, edtMakeupOtherBrand, "makeup");

    }

    private void clearServiceData() {

        if (getIntent().getStringExtra("editMode").equalsIgnoreCase("edit")) {

            //  MyApp.realm.beginTransaction();
//TODO for design
            surveyDetail.setSurvOtrService("");
            surveyDetail.setStrEdtHairOtherBrand("");
            surveyDetail.setStrEdtMakeupOtherBrand("");
            surveyDetail.setStrEdtNailsOtherBrand("");
            surveyDetail.setStrEdtSkinOtherBrand("");
            surveyDetail.setStrEdtSpaOtherBrand("");
            surveyDetail.setSurvBrandUsedHair("");
            surveyDetail.setSurvBrandUsedMakeup("");
            surveyDetail.setSurvBrandUsedNails("");
            surveyDetail.setSurvBrandUsedSkin("");
            surveyDetail.setSurvBrandUsedSpa("");
            //MyApp.realm.commitTransaction();
//TODO for design
        }
        setCheckBoxInit();

        edtNoOfOptHair.setText("");
        edtNoOfOptSkin.setText("");
        edtNoOfOptNails.setText("");
        edtNoOfOptSpa.setText("");
        edtNoOfOptMakeup.setText("");
        edtHairOtherBrand.setText("");
        edtSkinOtherBrand.setText("");
        edtNailsOtherBrand.setText("");
        edtSpaOtherBrand.setText("");
        edtMakeupOtherBrand.setText("");

        cbIsHair.setChecked(false);
        cbIsSkin.setChecked(false);
        cbIsNails.setChecked(false);
        cbIsSpa.setChecked(false);
        cbIsMakeup.setChecked(false);
        cbIsOtherService.setChecked(false);
        cbIsHairCheck.setChecked(false);
        cbIsSkinCheck.setChecked(false);
        cbIsNailsCheck.setChecked(false);
        cbIsSpaCheck.setChecked(false);
        cbIsMakeupCheck.setChecked(false);
        cbIsOtherServiceCheck.setChecked(false);

        //for refresh checkboxes of brands used
        linHairBlock.setVisibility(View.VISIBLE);
        linSkinBlock.setVisibility(View.VISIBLE);
        linNailsBlock.setVisibility(View.VISIBLE);
        linSpaBlock.setVisibility(View.VISIBLE);
        linMakeupBlock.setVisibility(View.VISIBLE);
        linOtherServiceBlockContainer.setVisibility(View.VISIBLE);

        linHairBlock.setVisibility(View.GONE);
        linSkinBlock.setVisibility(View.GONE);
        linNailsBlock.setVisibility(View.GONE);
        linSpaBlock.setVisibility(View.GONE);
        linMakeupBlock.setVisibility(View.GONE);
        linOtherServiceBlockContainer.setVisibility(View.GONE);
    }

    private void setInputLayout(final TextInputLayout textInputLayout, EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().isEmpty()) {
                    textInputLayout.setError("");
                    textInputLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String value = editable.toString();

                Pattern letter = Pattern.compile("[a-zA-z0-9]");
                Matcher hasLetter = letter.matcher(value);

                if (!hasLetter.find()) {
                    if (!value.isEmpty()) {
                        textInputLayout.setError(getResources().getString(R.string.should_not_spec_char));
                    }
                }
            }
        });

    }

    private void setSpinner() {

        //defalut set value to city
        List<KeyPairBoolData> listArray3 = new ArrayList<>();
        spCity.setItems(listArray3, new SingleSpinnerListener() {


            @Override
            public void onItemsSelected(KeyPairBoolData items) {
                if (items.isSelected()) {
                    Log.i(TAG, items.getName() + " : " + items.isSelected());
                    strCity = items.getName();
                }
            }

            @Override
            public void onClear() {

            }
        });


        Set<String> states = new HashSet<>();
        //location city state
        ArrayList<String> statesNames = new ArrayList<>();
        Gson gson = new Gson();
        stateCityBean = gson.fromJson(Utils.loadJSONFromAsset(ExpressInterestActivity.this), StateCityBean.class);
        for (int i = 0; i < stateCityBean.getCities().size(); i++) {
            StateCityBean.City city = stateCityBean.getCities().get(i);
            Log.i("Details-->", city.getState());

            states.add(city.getState());

        }

        statesNames = new ArrayList<>(states);
        final List<KeyPairBoolData> listArray2 = new ArrayList<>();


        for (int i = 0; i < statesNames.size(); i++) {
            KeyPairBoolData h = new KeyPairBoolData();
            h.setId(i + 1);
            if (getIntent().getStringExtra("editMode").equalsIgnoreCase("edit")) {
                if (surveyDetail.getSurvState().equalsIgnoreCase(statesNames.get(i))) {
                    h.setName(statesNames.get(i));  //selected state
                    h.setSelected(true);
                    strState = surveyDetail.getSurvState();
                    listArray3 = new ArrayList<>();
                    for (int j = 0; j < stateCityBean.getCities().size(); j++) {
                        StateCityBean.City city = stateCityBean.getCities().get(j);
                        if (statesNames.get(i).equalsIgnoreCase(city.getState())) {
                            Log.i("Details-->", city.getName());
                            KeyPairBoolData h1 = new KeyPairBoolData();
                            h1.setId(i + 1);
                            if (surveyDetail.getSurvCity().equalsIgnoreCase(city.getName())) {
                                h1.setName(city.getName());
                                h1.setSelected(true);
                                strCity = surveyDetail.getSurvCity();
                            } else {
                                h1.setName(city.getName());
                                h1.setSelected(false);
                            }
                            listArray3.add(h1);
                            //  Toast.makeText(ExpressInterestActivity.this, spCity.getSelectedItems().toString(),Toast.LENGTH_SHORT).show();
//                            spCity.setColorseparation(true);
                            spCity.setItems(listArray3,  new SingleSpinnerListener() {

                                @Override
                                public void onItemsSelected(KeyPairBoolData items) {
                                    if (items.isSelected()) {
                                        Log.i(TAG, " : " + items.getName() + " : " + items.isSelected());
                                        strCity = items.getName();
                                    }
                                }

                                @Override
                                public void onClear() {

                                }


                            });


                        }
                    }

                } else {
                    h.setName(statesNames.get(i));
                    h.setSelected(false);
                }
                listArray2.add(h);
            } else {
                h.setName(statesNames.get(i));
                h.setSelected(false);
                listArray2.add(h);
            }
        }


//        spState.setColorseparation(true);
        spState.setItems(listArray2,  new SingleSpinnerListener() {

            @Override
            public void onItemsSelected(KeyPairBoolData items) {
                final List<KeyPairBoolData> listArray3 = new ArrayList<>();
                if (items.isSelected()) {
                    Log.i(TAG, " : " + items.getName() + " : " + items.isSelected());
                    strState = items.getName();
                    for (int j = 0; j < stateCityBean.getCities().size(); j++) {
                        StateCityBean.City city = stateCityBean.getCities().get(j);
                        if (spState.getSelectedItem().toString().equalsIgnoreCase(city.getState())) {
                            Log.i("Details-->", city.getName());
                            KeyPairBoolData h = new KeyPairBoolData();
                            h.setId(j + 1);
                            h.setName(city.getName());
                            h.setSelected(false);
                            listArray3.add(h);
                        }
                    }

//                        spCity.setColorseparation(true);
                    spCity.setItems(listArray3,  new SingleSpinnerListener() {

                        @Override
                        public void onItemsSelected(KeyPairBoolData items) {
                            if (items.isSelected()) {
                                Log.i(TAG, " : " + items.getName() + " : " + items.isSelected());
                                strCity = items.getName();
                            }
                        }

                        @Override
                        public void onClear() {

                        }


                    });


                }
               /* for (int i = 0; i < items.size(); i++) {

                }*/
            }

            @Override
            public void onClear() {

            }
        });
    }

    //common method for checkbox init and assign listener
    private void checkBoxInit(CheckBox checkBox, int cbId, final Set<String> stringSet, final EditText editText, final String serviceType) {
        checkBox = findViewById(cbId);
        final CheckBox finalCheckBox = checkBox;
        if (clearData) {
            finalCheckBox.setChecked(false);
        }
        if (getIntent().getStringExtra("editMode").equalsIgnoreCase("edit")) {
            if (serviceType.equalsIgnoreCase("hair")) {
                if (editText != null) {
                    if (surveyDetail.getStrEdtHairOtherBrand() != null) {
                        if (!surveyDetail.getStrEdtHairOtherBrand().isEmpty()) {
                            editText.setVisibility(View.VISIBLE);
                            finalCheckBox.setChecked(true);
                        }
                    }
                } else {
                    if (surveyDetail.getSurvBrandUsedHair().contains(finalCheckBox.getText().toString())) {
                        finalCheckBox.setChecked(true);
                        stringSet.add(finalCheckBox.getText().toString());
                    }

                }
            } else if (serviceType.equalsIgnoreCase("skin")) {
                if (editText != null) {
                    if (surveyDetail.getStrEdtSkinOtherBrand() != null) {
                        if (!surveyDetail.getStrEdtSkinOtherBrand().isEmpty()) {
                            editText.setVisibility(View.VISIBLE);
                            finalCheckBox.setChecked(true);
                        }
                    }
                } else {
                    if (surveyDetail.getSurvBrandUsedSkin().contains(finalCheckBox.getText().toString())) {
                        finalCheckBox.setChecked(true);
                        stringSet.add(finalCheckBox.getText().toString());
                    }

                }

            } else if (serviceType.equalsIgnoreCase("nails")) {
                if (editText != null) {
                    if (surveyDetail.getStrEdtNailsOtherBrand() != null) {
                        if (!surveyDetail.getStrEdtNailsOtherBrand().isEmpty()) {
                            editText.setVisibility(View.VISIBLE);
                            finalCheckBox.setChecked(true);
                        }
                    }
                } else {
                    if (surveyDetail.getSurvBrandUsedNails().contains(finalCheckBox.getText().toString())) {
                        finalCheckBox.setChecked(true);
                        stringSet.add(finalCheckBox.getText().toString());
                    }
                }

            } else if (serviceType.equalsIgnoreCase("spa")) {
                if (editText != null) {
                    if (surveyDetail.getStrEdtSpaOtherBrand() != null) {
                        if (!surveyDetail.getStrEdtSpaOtherBrand().isEmpty()) {
                            editText.setVisibility(View.VISIBLE);
                            finalCheckBox.setChecked(true);
                        }
                    }
                } else {
                    if (surveyDetail.getSurvBrandUsedSpa().contains(finalCheckBox.getText().toString())) {
                        finalCheckBox.setChecked(true);
                        stringSet.add(finalCheckBox.getText().toString());
                    }
                }
            } else if (serviceType.equalsIgnoreCase("makeup")) {
                if (editText != null) {
                    if (surveyDetail.getStrEdtMakeupOtherBrand() != null) {
                        if (!surveyDetail.getStrEdtMakeupOtherBrand().isEmpty()) {
                            editText.setVisibility(View.VISIBLE);
                            finalCheckBox.setChecked(true);
                        }
                    }
                } else {
                    if (surveyDetail.getSurvBrandUsedMakeup().contains(finalCheckBox.getText().toString())) {
                        finalCheckBox.setChecked(true);
                        stringSet.add(finalCheckBox.getText().toString());
                    }

                }


            }
        }
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (getIntent().getStringExtra("editMode").equalsIgnoreCase("edit")) {

                    if (isChecked) {
                        if (stringSet == null) {
                            editText.setVisibility(View.VISIBLE);
                        } else {
                            stringSet.add(finalCheckBox.getText().toString());
                            setCheckBoxChecked(true, serviceType);
                        }
                    } else {
                        if (stringSet == null) {
                            editText.setVisibility(View.GONE);
                        } else {
                            stringSet.remove(finalCheckBox.getText().toString());
                            if (stringSet.size() == 0) {
                                setCheckBoxChecked(false, serviceType);
                            }
                        }
                    }
                } else {
                    if (isChecked) {
                        if (stringSet == null) {
                            editText.setVisibility(View.VISIBLE);
                        } else {
                            stringSet.add(finalCheckBox.getText().toString());
                            setCheckBoxChecked(true, serviceType);
                        }
                    } else {
                        if (stringSet == null) {
                            editText.setVisibility(View.GONE);
                        } else {
                            stringSet.remove(finalCheckBox.getText().toString());
                            if (stringSet.size() == 0) {
                                setCheckBoxChecked(false, serviceType);
                            }
                        }
                    }
                }
            }
        });


    }

    private void setCheckBoxChecked(boolean check, String serviceType) {
        if (serviceType.equalsIgnoreCase("hair")) {
            cbIsHairCheck.setChecked(check);
        } else if (serviceType.equalsIgnoreCase("skin")) {
            cbIsSkinCheck.setChecked(check);
        } else if (serviceType.equalsIgnoreCase("nails")) {
            cbIsNailsCheck.setChecked(check);
        } else if (serviceType.equalsIgnoreCase("spa")) {
            cbIsSpaCheck.setChecked(check);
        } else if (serviceType.equalsIgnoreCase("makeup")) {
            cbIsMakeupCheck.setChecked(check);
        }
    }

    private void setEditSalonData() {
        edtSalonName.setText(surveyDetail.getSurvName());
        edtManName.setText(surveyDetail.getSurvManName());
        edtAddress.setText(surveyDetail.getSurvAddr());
        edtPinCode.setText(surveyDetail.getSurvPinCode());
        edtContNumber.setText(surveyDetail.getSurvSalNumber());
        edtManContNumber.setText(surveyDetail.getSurvManNumber());
        edtWebsite.setText(surveyDetail.getSurvWebsite());
        edtEmailId.setText(surveyDetail.getSurvEmail());
        edtFacebook.setText(surveyDetail.getSurvFacebook());
        edtInstagram.setText(surveyDetail.getSurvInstagram());

        edtNoOfOptHair.setText(surveyDetail.getSurvNoHair());
        edtNoOfOptSkin.setText(surveyDetail.getSurvNoSkin());
        edtNoOfOptNails.setText(surveyDetail.getSurvNoNails());
        edtNoOfOptSpa.setText(surveyDetail.getSurvNoSpa());
        edtNoOfOptMakeup.setText(surveyDetail.getSurvNoMakeup());

        edtHairOtherBrand.setText(surveyDetail.getStrEdtHairOtherBrand());
        edtSkinOtherBrand.setText(surveyDetail.getStrEdtSkinOtherBrand());
        edtNailsOtherBrand.setText(surveyDetail.getStrEdtNailsOtherBrand());
        edtSpaOtherBrand.setText(surveyDetail.getStrEdtSpaOtherBrand());
        edtMakeupOtherBrand.setText(surveyDetail.getStrEdtMakeupOtherBrand());

        tvEstYear.setText(surveyDetail.getSurvEstblishYear());
        edtOwnerName.setText(surveyDetail.getSurvOwnerName());
        edtOwnContNumber.setText(surveyDetail.getSurvOwnNumber());
        edtOwnEdu.setText(surveyDetail.getSurvOwnEdu());

        if (surveyDetail.getSurvWorkHrs() != null && !surveyDetail.getSurvWorkHrs().isEmpty()) {
            tvFromTime.setText(surveyDetail.getSurvWorkHrs().split("-")[0]);
            tvToTime.setText(surveyDetail.getSurvWorkHrs().split("-")[1]);
        }

        noOfHKStaff.setText(surveyDetail.getSurvHouseStafCount());
        noOfMgmtStaff.setText(surveyDetail.getSurvMgmtStaffCount());
        noOfHairSkinChairs.setText(surveyDetail.getSurvHairSkinChairs());

        edtInstitueCert.setText(surveyDetail.getSurvCertification());

        //Salon / Institute
        if (surveyDetail.getSurvCatMain().equalsIgnoreCase("Salon")) {
            spMainCategory.setSelection(1);
            strCatMain = spMainCategory.getSelectedItem().toString();


            if (surveyDetail.getSurvNoHair() != null && !surveyDetail.getSurvNoHair().isEmpty()
                    || surveyDetail.getSurvBrandUsedHair() != null && !surveyDetail.getSurvBrandUsedHair().isEmpty()
                    || surveyDetail.getStrEdtHairOtherBrand() != null && !surveyDetail.getStrEdtHairOtherBrand().isEmpty()) {
                cbIsHairCheck.setChecked(true);
            }
            if (surveyDetail.getSurvNoSkin() != null && !surveyDetail.getSurvNoSkin().isEmpty()
                    || surveyDetail.getSurvBrandUsedSkin() != null && !surveyDetail.getSurvBrandUsedSkin().isEmpty()
                    || surveyDetail.getStrEdtSkinOtherBrand() != null && !surveyDetail.getStrEdtSkinOtherBrand().isEmpty()) {
                cbIsSkinCheck.setChecked(true);
            }
            if (surveyDetail.getSurvNoNails() != null && !surveyDetail.getSurvNoNails().isEmpty()
                    || surveyDetail.getSurvBrandUsedNails() != null && !surveyDetail.getSurvBrandUsedNails().isEmpty()
                    || surveyDetail.getStrEdtNailsOtherBrand() != null && !surveyDetail.getStrEdtNailsOtherBrand().isEmpty()) {
                cbIsNailsCheck.setChecked(true);
            }
            if (surveyDetail.getSurvNoSpa() != null && !surveyDetail.getSurvNoSpa().isEmpty()
                    || surveyDetail.getSurvBrandUsedSpa() != null && !surveyDetail.getSurvBrandUsedSpa().isEmpty()
                    || surveyDetail.getStrEdtSpaOtherBrand() != null && !surveyDetail.getStrEdtSpaOtherBrand().isEmpty()) {
                cbIsSpaCheck.setChecked(true);
            }
            if (surveyDetail.getSurvNoMakeup() != null && !surveyDetail.getSurvNoMakeup().isEmpty()
                    || surveyDetail.getSurvBrandUsedMakeup() != null && !surveyDetail.getSurvBrandUsedMakeup().isEmpty()
                    || surveyDetail.getStrEdtMakeupOtherBrand() != null && !surveyDetail.getStrEdtMakeupOtherBrand().isEmpty()) {
                cbIsMakeupCheck.setChecked(true);
            }
            if (surveyDetail.getSurvOtrService() != null && !surveyDetail.getSurvOtrService().isEmpty()) {
                try {
                    if (new JSONArray(surveyDetail.getSurvOtrService()).length() > 0) {
                        cbIsOtherServiceCheck.setChecked(true);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        } else if (surveyDetail.getSurvCatMain().equalsIgnoreCase("Institute")) {
            spMainCategory.setSelection(2);
            strCatMain = spMainCategory.getSelectedItem().toString();

            if (!surveyDetail.getSurvNoHair().isEmpty()) {
                cbIsHairCheck.setChecked(true);
            }
            if (!surveyDetail.getSurvNoSkin().isEmpty()) {
                cbIsSkinCheck.setChecked(true);
            }
            if (!surveyDetail.getSurvNoNails().isEmpty()) {
                cbIsNailsCheck.setChecked(true);
            }
            if (!surveyDetail.getSurvNoSpa().isEmpty()) {
                cbIsSpaCheck.setChecked(true);
            }
            if (!surveyDetail.getSurvNoMakeup().isEmpty()) {
                cbIsMakeupCheck.setChecked(true);
            }
            if (!surveyDetail.getSurvOtrService().isEmpty()) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(surveyDetail.getSurvOtrService());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (jsonArray.length() > 0) {
                    cbIsOtherServiceCheck.setChecked(true);
                }
            }
        }

        //gst yes/no
        if (surveyDetail.getSurvGst().equalsIgnoreCase("Yes")) {
            rbYes.setChecked(true);
            strGstYesNo = rbYes.getText().toString();
            edtGstNo.setText(surveyDetail.getSurvGstNo());
            strEdtGstNo = surveyDetail.getSurvGstNo();
            if (!surveyDetail.getSurvRateOfGst().isEmpty()) {
                ArrayList<String> gstArrList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.gst_rate_array)));
                spGstInPer.setSelection(gstArrList.indexOf(surveyDetail.getSurvRateOfGst()));
                strGstRate = surveyDetail.getSurvRateOfGst();
            }
        } else {
            rbNo.setChecked(true);
            strGstYesNo = rbNo.getText().toString();
        }

//infra yes/no
        if (surveyDetail.getSurvResParking().equalsIgnoreCase("Yes")) {
            rpYes.setChecked(true);
        } else {
            rpNo.setChecked(true);
        }

        if (surveyDetail.getSurvTv().equalsIgnoreCase("Yes")) {
            tvYes.setChecked(true);
        } else {
            tvNo.setChecked(true);
        }

        if (surveyDetail.getSurvFreeWifi().equalsIgnoreCase("Yes")) {
            wfYes.setChecked(true);
        } else {
            wfNo.setChecked(true);
        }

        if (surveyDetail.getSurvAc().equalsIgnoreCase("Yes")) {
            acYes.setChecked(true);
        } else {
            acNo.setChecked(true);
        }


    }

    private void saveDataInLocalBD() {
//mandatory fields
        strEdtSalonName = edtSalonName.getText().toString();
        strEdtManName = edtManName.getText().toString();
        strEdtAddress = edtAddress.getText().toString();
        strEdtPinCode = edtPinCode.getText().toString();
        strTvEstYear = tvEstYear.getText().toString();
        strTvFromTime = tvFromTime.getText().toString();
        strTvToTime = tvToTime.getText().toString();
        strEdtContNumber = edtContNumber.getText().toString();
        strEdtManContNumber = edtManContNumber.getText().toString();
        strEdtGstNo = edtGstNo.getText().toString();
        strEdtInstitueCert = edtInstitueCert.getText().toString();
        strEdtWebsite = edtWebsite.getText().toString();
        strEdtEmailId = edtEmailId.getText().toString();
        strEdtFacebook = edtFacebook.getText().toString();
        strEdtInstagram = edtInstagram.getText().toString();
        strEdtOwnerName = edtOwnerName.getText().toString();
        strEdtOwnContNumber = edtOwnContNumber.getText().toString();
        strEdtOwnEdu = edtOwnEdu.getText().toString();

        strEdtNoOfOptHair = edtNoOfOptHair.getText().toString();
        strEdtNoOfOptSkin = edtNoOfOptSkin.getText().toString();
        strEdtNoOfOptNails = edtNoOfOptNails.getText().toString();
        strEdtNoOfOptSpa = edtNoOfOptSpa.getText().toString();
        strEdtNoOfOptMakeup = edtNoOfOptMakeup.getText().toString();

        strEdtHairOtherBrand = edtHairOtherBrand.getText().toString();
        strEdtSkinOtherBrand = edtSkinOtherBrand.getText().toString();
        strEdtNailsOtherBrand = edtNailsOtherBrand.getText().toString();
        strEdtSpaOtherBrand = edtSpaOtherBrand.getText().toString();
        strEdtMakeupOtherBrand = edtMakeupOtherBrand.getText().toString();

        strImgPaths = TextUtils.join(",", imgArr);  //images path of comma separated
        try {
            strRbRP = ((RadioButton) findViewById(rgRP.getCheckedRadioButtonId())).getText().toString();
        } catch (Exception e) {
            strRbRP = "";
            e.printStackTrace();
        }
        try {
            strRbTV = ((RadioButton) findViewById(rgTV.getCheckedRadioButtonId())).getText().toString();
        } catch (Exception e) {
            strRbTV = "";
            e.printStackTrace();
        }
        try {
            strRbWF = ((RadioButton) findViewById(rgWF.getCheckedRadioButtonId())).getText().toString();
        } catch (Exception e) {
            strRbWF = "";
            e.printStackTrace();
        }
        try {
            strRbAc = ((RadioButton) findViewById(rgAc.getCheckedRadioButtonId())).getText().toString();
        } catch (Exception e) {
            strRbAc = "";
            e.printStackTrace();
        }
        strNoOfHKStaff = noOfHKStaff.getText().toString();
        strNoOfMgmtStaff = noOfMgmtStaff.getText().toString();
        strNoOfHairSkinChairs = noOfHairSkinChairs.getText().toString();

        Calendar startEndTimeCal = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-YYYY HH:mm:ss");
        strEndTime = simpleDateFormat.format(startEndTimeCal.getTimeInMillis());


        strGstYesNo = ((RadioButton) findViewById(rgGst.getCheckedRadioButtonId())).getText().toString();
        allGone(0);
        if (strCatMain.isEmpty()) {
            showFailToast(ExpressInterestActivity.this, getResources().getString(R.string.please_select_category));
        } else if (strEdtSalonName.isEmpty()) {
            if (strCatMain.equalsIgnoreCase("Salon")) {
                tilSalonName.setError(getResources().getString(R.string.enter_salon_name));
            } else {
                tilSalonName.setError(getResources().getString(R.string.enter_insti_name));
            }
            edtSalonName.requestFocus();
            cvSecond.setVisibility(View.VISIBLE);
            setDrawableTextView(R.drawable.ic_keyboard_arrow_up_black_24dp, tvSalonDetails);
        } else if (strEdtManName.isEmpty()) {
            tilManName.setError(getResources().getString(R.string.enter_manager_name));
            edtManName.requestFocus();
            cvSecond.setVisibility(View.VISIBLE);
            setDrawableTextView(R.drawable.ic_keyboard_arrow_up_black_24dp, tvSalonDetails);
        } else if (strEdtAddress.isEmpty()) {
            tilAddress.setError(getResources().getString(R.string.enter_address));
            edtAddress.requestFocus();
            cvSecond.setVisibility(View.VISIBLE);
            setDrawableTextView(R.drawable.ic_keyboard_arrow_up_black_24dp, tvSalonDetails);
        } else if (strState.isEmpty()) {
            showFailToast(ExpressInterestActivity.this, getResources().getString(R.string.please_select_state));
            cvSecond.setVisibility(View.VISIBLE);
            setDrawableTextView(R.drawable.ic_keyboard_arrow_up_black_24dp, tvSalonDetails);
        } else if (strCity.isEmpty()) {
            showFailToast(ExpressInterestActivity.this, getResources().getString(R.string.please_select_city));
            cvSecond.setVisibility(View.VISIBLE);
            setDrawableTextView(R.drawable.ic_keyboard_arrow_up_black_24dp, tvSalonDetails);
        } else if (strEdtPinCode.isEmpty()) {
            tilPinCode.setError(getResources().getString(R.string.enter_pin_code));
            edtPinCode.requestFocus();
            cvSecond.setVisibility(View.VISIBLE);
            setDrawableTextView(R.drawable.ic_keyboard_arrow_up_black_24dp, tvSalonDetails);
        } else if (strEdtPinCode.length() < 6) {
            tilPinCode.setError(getResources().getString(R.string.six_digits_pincode));
            edtPinCode.requestFocus();
            cvSecond.setVisibility(View.VISIBLE);
            setDrawableTextView(R.drawable.ic_keyboard_arrow_up_black_24dp, tvSalonDetails);
        } else if (strTvFromTime.isEmpty()) {
            tvFromTime.setError(getResources().getString(R.string.mandatory_field_msg));
            tvFromTime.requestFocus();
            cvSecond.setVisibility(View.VISIBLE);
            setDrawableTextView(R.drawable.ic_keyboard_arrow_up_black_24dp, tvSalonDetails);
        } else if (strTvToTime.isEmpty()) {
            tvToTime.setError(getResources().getString(R.string.mandatory_field_msg));
            tvToTime.requestFocus();
            cvSecond.setVisibility(View.VISIBLE);
            setDrawableTextView(R.drawable.ic_keyboard_arrow_up_black_24dp, tvSalonDetails);
        } else if (strEdtContNumber.isEmpty()) {
            tilContactNumber.setError(getResources().getString(R.string.enter_contact_number));
            edtContNumber.requestFocus();
            cvSecond.setVisibility(View.VISIBLE);
            setDrawableTextView(R.drawable.ic_keyboard_arrow_up_black_24dp, tvSalonDetails);
        } else if (strEdtContNumber.length() < 10) {
            tilContactNumber.setError(getResources().getString(R.string.enter_ten_digits));
            edtContNumber.requestFocus();
            cvSecond.setVisibility(View.VISIBLE);
            setDrawableTextView(R.drawable.ic_keyboard_arrow_up_black_24dp, tvSalonDetails);
        } else if (!strEdtManContNumber.isEmpty() && strEdtManContNumber.length() < 10) {
            tilManContNumber.setError(getResources().getString(R.string.enter_ten_digits));
            edtManContNumber.requestFocus();
            cvSecond.setVisibility(View.VISIBLE);
            setDrawableTextView(R.drawable.ic_keyboard_arrow_up_black_24dp, tvSalonDetails);
        } else if (strGstYesNo.isEmpty()) {
            showFailToast(ExpressInterestActivity.this, getResources().getString(R.string.select_gst_yes_no));
            cvSecond.setVisibility(View.VISIBLE);
            setDrawableTextView(R.drawable.ic_keyboard_arrow_up_black_24dp, tvSalonDetails);
        } else if (strGstYesNo.equalsIgnoreCase("Yes") && strEdtGstNo.isEmpty() && (strEdtGstNo.length() < 15)) {
            tilGstNo.setError(getResources().getString(R.string.fifteen_digits_gst_number));
            edtGstNo.requestFocus();
            cvSecond.setVisibility(View.VISIBLE);
            setDrawableTextView(R.drawable.ic_keyboard_arrow_up_black_24dp, tvSalonDetails);
        } else if (strGstYesNo.equalsIgnoreCase("Yes") && strGstRate.isEmpty()) {
            showFailToast(ExpressInterestActivity.this, "Please Select GST Rate");
            edtGstNo.requestFocus();
            cvSecond.setVisibility(View.VISIBLE);
            setDrawableTextView(R.drawable.ic_keyboard_arrow_up_black_24dp, tvSalonDetails);
        } else if (!strEdtWebsite.isEmpty() && !Patterns.WEB_URL.matcher(strEdtWebsite).matches()) {
            tilWebsite.setError(getResources().getString(R.string.valid_web_url));
            edtWebsite.requestFocus();
            cvSocMed.setVisibility(View.VISIBLE);
            setDrawableTextView(R.drawable.ic_keyboard_arrow_up_black_24dp, tvSocialMediaLbl);
        } else if (strEdtEmailId.isEmpty()) {
            tilEmailId.setError(getResources().getString(R.string.valid_email_id));
            edtEmailId.requestFocus();
            cvSocMed.setVisibility(View.VISIBLE);
            setDrawableTextView(R.drawable.ic_keyboard_arrow_up_black_24dp, tvSocialMediaLbl);
        } else if (!Patterns.EMAIL_ADDRESS.matcher(strEdtEmailId).matches()) {
            tilEmailId.setError(getResources().getString(R.string.valid_email_id));
            edtEmailId.requestFocus();
            cvSocMed.setVisibility(View.VISIBLE);
            setDrawableTextView(R.drawable.ic_keyboard_arrow_up_black_24dp, tvSocialMediaLbl);
        } else if (strEdtOwnerName.isEmpty()) {
            tilOwnName.setError(getResources().getString(R.string.enter_owner_name));
            edtOwnerName.requestFocus();
            cvFourth.setVisibility(View.VISIBLE);
            setDrawableTextView(R.drawable.ic_keyboard_arrow_up_black_24dp, tvOwnerDetails);
        } else if (strEdtOwnContNumber.isEmpty()) {
            tilOwnContNumber.setError(getResources().getString(R.string.enter_contact_number));
            edtOwnContNumber.requestFocus();
            cvFourth.setVisibility(View.VISIBLE);
            setDrawableTextView(R.drawable.ic_keyboard_arrow_up_black_24dp, tvOwnerDetails);
        } else if (strEdtOwnContNumber.length() < 10) {
            tilOwnContNumber.setError(getResources().getString(R.string.enter_ten_digits));
            edtOwnContNumber.requestFocus();
            cvFourth.setVisibility(View.VISIBLE);
            setDrawableTextView(R.drawable.ic_keyboard_arrow_up_black_24dp, tvOwnerDetails);
        } else if (strNoOfHKStaff.isEmpty()) {
            tilNoOfHouseKeepStaff.setError(getResources().getString(R.string.enter_noof_staff));
            noOfHKStaff.requestFocus();
            cvTenth.setVisibility(View.VISIBLE);
            setDrawableTextView(R.drawable.ic_keyboard_arrow_up_black_24dp, tvInfraStDetails);
        } else if (strNoOfMgmtStaff.isEmpty()) {
            tilNoOfMgmtStaff.setError(getResources().getString(R.string.enter_noof_staff));
            noOfMgmtStaff.requestFocus();
            cvTenth.setVisibility(View.VISIBLE);
            setDrawableTextView(R.drawable.ic_keyboard_arrow_up_black_24dp, tvInfraStDetails);
        } else if (strNoOfHairSkinChairs.isEmpty()) {
            tilNoOfSkinChair.setError(getResources().getString(R.string.enter_noof_chairs));
            noOfHairSkinChairs.requestFocus();
            cvTenth.setVisibility(View.VISIBLE);
            setDrawableTextView(R.drawable.ic_keyboard_arrow_up_black_24dp, tvInfraStDetails);
        } else {
           /* Realm.init(ExpressInterestActivity.this);
            Realm realm = Realm.getDefaultInstance();*/
            if (b) {
                addSalonBean = new SurveyDetail();

                if (getIntent().getStringExtra("editMode").equalsIgnoreCase("edit")) {

                    addSalonBean.setSurvId(surveyDetail.getSurvId());

                } else {
                    addSalonBean.setSurvId("");
                }

                addSalonBean.setSurvName(strEdtSalonName);
                addSalonBean.setSurvManName(strEdtManName);
                addSalonBean.setSurvAddr(strEdtAddress);
                addSalonBean.setSurvState(strState);
                addSalonBean.setSurvCity(strCity);
                addSalonBean.setSurvPinCode(strEdtPinCode);
                addSalonBean.setSurvEstblishYear(strTvEstYear);
                addSalonBean.setSurvStartTime(strStartTime);
                addSalonBean.setSurvEndTime(strEndTime);
                addSalonBean.setSurvWorkHrs(strTvFromTime + "-" + strTvToTime);
                addSalonBean.setSurvSalNumber(strEdtContNumber);
                addSalonBean.setSurvManNumber(strEdtManContNumber);
                addSalonBean.setSurvGst(strGstYesNo);
                addSalonBean.setSurvRateOfGst(strGstRate);
                addSalonBean.setSurvGstNo(strEdtGstNo);
                addSalonBean.setSurvCertification(strEdtInstitueCert);
                addSalonBean.setSurvWebsite(strEdtWebsite);
                addSalonBean.setSurvEmail(strEdtEmailId);
                addSalonBean.setSurvFacebook(strEdtFacebook);
                addSalonBean.setSurvInstagram(strEdtInstagram);
                addSalonBean.setSurvOwnerName(strEdtOwnerName);
                addSalonBean.setSurvOwnNumber(strEdtOwnContNumber);
                addSalonBean.setSurvOwnEdu(strEdtOwnEdu);
                addSalonBean.setSurvNoHair(strEdtNoOfOptHair);
                addSalonBean.setSurvNoSkin(strEdtNoOfOptSkin);
                addSalonBean.setSurvNoNails(strEdtNoOfOptNails);
                addSalonBean.setSurvNoSpa(strEdtNoOfOptSpa);
                addSalonBean.setSurvNoMakeup(strEdtNoOfOptMakeup);
                addSalonBean.setSurvBrandUsedHair(TextUtils.join(",", hairSet));
                addSalonBean.setSurvBrandUsedSkin(TextUtils.join(",", skinSet));
                addSalonBean.setSurvBrandUsedNails(TextUtils.join(",", nailsSet));
                addSalonBean.setSurvBrandUsedSpa(TextUtils.join(",", spaSet));
                addSalonBean.setSurvBrandUsedMakeup(TextUtils.join(",", makeupSet));
                addSalonBean.setStrEdtHairOtherBrand(strEdtHairOtherBrand);
                addSalonBean.setStrEdtSkinOtherBrand(strEdtSkinOtherBrand);
                addSalonBean.setStrEdtNailsOtherBrand(strEdtNailsOtherBrand);
                addSalonBean.setStrEdtSpaOtherBrand(strEdtSpaOtherBrand);
                addSalonBean.setStrEdtMakeupOtherBrand(strEdtMakeupOtherBrand);
                addSalonBean.setSurvHouseStafCount(strNoOfHKStaff);
                addSalonBean.setSurvMgmtStaffCount(strNoOfMgmtStaff);
                addSalonBean.setSurvHairSkinChairs(strNoOfHairSkinChairs);
                addSalonBean.setSurvResParking(strRbRP);
                addSalonBean.setSurvTv(strRbTV);
                addSalonBean.setSurvFreeWifi(strRbWF);
                addSalonBean.setSurvAc(strRbAc);
                addSalonBean.setSurvLat(latitude);
                addSalonBean.setSurvLon(longitude);
                if(strCatMain.equalsIgnoreCase("Salon")) {
                    addSalonBean.setSurvCatMain("0");  //Salon
                }else {
                    addSalonBean.setSurvCatMain("1");  //Institute
                }
                addSalonBean.setSurvImg(strImgPaths);
                addSalonBean.setSurvStatus("0");
                addSalonBean.setSurvUserId(userId);

                for (int i = 0; i < insertPoint.getChildCount(); i++) {
                    View v = insertPoint.getChildAt(i);
                    EditText otherServiceName = v.findViewById(R.id.otherServiceName);
                    EditText otherServiceNoofOpt = v.findViewById(R.id.otherServiceNoofOpt);
                    EditText otherSerOtherBrand = v.findViewById(R.id.otherSerOtherBrand);

                    JSONObject jsonObject = new JSONObject();
                    try {
                        if (otherServiceName.getText().toString().isEmpty() && otherServiceNoofOpt.getText().toString().isEmpty() && otherSerOtherBrand.getText().toString().isEmpty()) {
                        } else {
                            jsonObject.put("otherServiceName", otherServiceName.getText().toString());
                            jsonObject.put("otherServiceNoofOpt", otherServiceNoofOpt.getText().toString());
                            jsonObject.put("otherSerOtherBrand", otherSerOtherBrand.getText().toString());
                            othServiceData.put(jsonObject);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                strOtherServicesArrayJson = othServiceData.toString();
                addSalonBean.setSurvOtrService(strOtherServicesArrayJson);


                addExpressInterest(addSalonBean);
              /*  MyApp.realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        if (getIntent().getStringExtra("editMode").equalsIgnoreCase("edit")) {
                            addSalonBean.setSurvLocalId(surveyDetail.getSurvLocalId());
                            addSalonBean.setSurvId(surveyDetail.getSurvId());
                            realm.insertOrUpdate(addSalonBean);
                            startActivity(new Intent(ExpressInterestActivity.this, PreviewSurveyDetailActivity.class).putExtra("surveyLocalId", surveyDetail.getSurvLocalId()));
                            finish();
                        } else {

                            Number currentIdNum = realm.where(SurveyDetail.class).max("survLocalId");
                            int nextId;
                            if (currentIdNum == null) {
                                nextId = 1;
                            } else {
                                nextId = currentIdNum.intValue() + 1;
                            }
                            addSalonBean.setSurvLocalId(nextId);
                            addSalonBean.setSurvStatus("0");
                            realm.insertOrUpdate(addSalonBean);

                            startActivity(new Intent(ExpressInterestActivity.this, PreviewSurveyDetailActivity.class).putExtra("surveyLocalId", nextId));
                            finish();
                        }

                    }
                });*/ //TODO for design
            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ExpressInterestActivity.this, LoginActivity.class));
        finish();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(ExpressInterestActivity.this, LoginActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tvEstYear:
                showYearDialog();
                break;
            case R.id.tvFromTime:
                tiemPicker("from");
                break;
            case R.id.tvToTime:
                tiemPicker("to");
                break;

        }
    }

    public void allGone(int blockServiceMenu) {
        linHairBlock.setVisibility(View.GONE);
        linSkinBlock.setVisibility(View.GONE);
        linNailsBlock.setVisibility(View.GONE);
        linSpaBlock.setVisibility(View.GONE);
        linMakeupBlock.setVisibility(View.GONE);
        linOtherServiceBlockContainer.setVisibility(View.GONE);
        ivAddView.setVisibility(View.GONE);
        cvSecond.setVisibility(View.GONE);
        cvSocMed.setVisibility(View.GONE);
        cvFourth.setVisibility(View.GONE);
        if (blockServiceMenu != 1) {  //if ==0
            cvEighth.setVisibility(View.GONE);
            setDrawableCheckbox(R.drawable.ic_keyboard_arrow_down_black_24dp, tvSerOffDetails);
            tvSerOffDetails.setChecked(false);
        }
        cvTenth.setVisibility(View.GONE);

        if (strCatMain.equalsIgnoreCase("Salon")) {
            setDrawableCheckbox(R.drawable.ic_keyboard_arrow_down_black_24dp, cbIsHair);
            setDrawableCheckbox(R.drawable.ic_keyboard_arrow_down_black_24dp, cbIsSkin);
            setDrawableCheckbox(R.drawable.ic_keyboard_arrow_down_black_24dp, cbIsNails);
            setDrawableCheckbox(R.drawable.ic_keyboard_arrow_down_black_24dp, cbIsSpa);
            setDrawableCheckbox(R.drawable.ic_keyboard_arrow_down_black_24dp, cbIsMakeup);
            setDrawableCheckbox(R.drawable.ic_keyboard_arrow_down_black_24dp, cbIsOtherService);
        } else if (strCatMain.equalsIgnoreCase("Institute")) {
            removeDrawableCheckbox(R.drawable.ic_keyboard_arrow_down_black_24dp, cbIsHair);
            removeDrawableCheckbox(R.drawable.ic_keyboard_arrow_down_black_24dp, cbIsSkin);
            removeDrawableCheckbox(R.drawable.ic_keyboard_arrow_down_black_24dp, cbIsNails);
            removeDrawableCheckbox(R.drawable.ic_keyboard_arrow_down_black_24dp, cbIsSpa);
            removeDrawableCheckbox(R.drawable.ic_keyboard_arrow_down_black_24dp, cbIsMakeup);
            removeDrawableCheckbox(R.drawable.ic_keyboard_arrow_down_black_24dp, cbIsOtherService);
            // linOtherServiceBlockContainer.setVisibility(View.VISIBLE);
        }

        setDrawableCheckbox(R.drawable.ic_keyboard_arrow_down_black_24dp, tvSalonDetails);
        setDrawableCheckbox(R.drawable.ic_keyboard_arrow_down_black_24dp, tvSocialMediaLbl);
        setDrawableCheckbox(R.drawable.ic_keyboard_arrow_down_black_24dp, tvOwnerDetails);
        setDrawableCheckbox(R.drawable.ic_keyboard_arrow_down_black_24dp, tvInfraStDetails);

           /* cbIsHair.setChecked(false);
            cbIsSkin.setChecked(false);
        cbIsNails.setChecked(false);
        cbIsSpa.setChecked(false);
        cbIsMakeup.setChecked(false);
        cbIsOtherService.setChecked(false);*/

        tvSocialMediaLbl.setChecked(false);
        tvOwnerDetails.setChecked(false);
        tvInfraStDetails.setChecked(false);
        tvSalonDetails.setChecked(false);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        switch (buttonView.getId()) {
            //for Institute only checked the checkbox
            case R.id.cbIsHairCheck:

                if (isChecked) {
                    if (strCatMain.equalsIgnoreCase("Institute")) {
                        allGone(1);
                        linHairBlock.setVisibility(View.GONE);
                        cbIsHairCheck.setChecked(true);
                        edtNoOfOptHair.setText("");
                        removeDrawableCheckbox(R.drawable.ic_keyboard_arrow_up_black_24dp, cbIsHair);
                    }


                } else {

                    if (strCatMain.equalsIgnoreCase("Institute")) {
                        allGone(1);
                        edtNoOfOptHair.setText("");
                        cbIsHairCheck.setChecked(false);
                        removeDrawableCheckbox(R.drawable.ic_keyboard_arrow_down_black_24dp, cbIsHair);
                    } else {
                        //   MyApp.realm.beginTransaction();
                        if (getIntent().getStringExtra("editMode").equalsIgnoreCase("edit")) {
                            surveyDetail.setStrEdtHairOtherBrand("");
                            surveyDetail.setSurvBrandUsedHair("");
                        }
                        //  MyApp.realm.commitTransaction();
                        checkBoxInit(hairBrand1, R.id.hairBrand1, hairSet, null, "hair");
                        checkBoxInit(hairBrand2, R.id.hairBrand2, hairSet, null, "hair");
                        checkBoxInit(hairBrand3, R.id.hairBrand3, hairSet, null, "hair");
                        checkBoxInit(hairBrand4, R.id.hairBrand4, hairSet, null, "hair");
                        checkBoxInit(hairBrand5, R.id.hairBrand5, hairSet, null, "hair");
                        checkBoxInit(hairBrand6, R.id.hairBrand6, hairSet, null, "hair");
                        checkBoxInit(hairBrand7, R.id.hairBrand7, hairSet, null, "hair");
                        checkBoxInit(hairBrand8, R.id.hairBrand8, hairSet, null, "hair");
                        checkBoxInit(hairBrand9, R.id.hairBrand9, hairSet, null, "hair");
                        checkBoxInit(hairBrand10, R.id.hairBrand10, null, edtHairOtherBrand, "hair");

                        edtNoOfOptHair.setText("");
                        edtHairOtherBrand.setText("");

                    }
                }
                break;
            case R.id.cbIsSkinCheck:

                if (isChecked) {
                    if (strCatMain.equalsIgnoreCase("Institute")) {
                        allGone(1);
                        cbIsSkinCheck.setChecked(true);
                        edtNoOfOptSkin.setText("");
                        linSkinBlock.setVisibility(View.GONE);
                        removeDrawableCheckbox(R.drawable.ic_keyboard_arrow_up_black_24dp, cbIsSkin);
                    }

                } else {
                    if (strCatMain.equalsIgnoreCase("Institute")) {
                        allGone(1);
                        cbIsSkinCheck.setChecked(false);
                        edtNoOfOptSkin.setText("");
                        linSkinBlock.setVisibility(View.GONE);
                        removeDrawableCheckbox(R.drawable.ic_keyboard_arrow_down_black_24dp, cbIsSkin);
                    } else {
                        // MyApp.realm.beginTransaction();
                        if (getIntent().getStringExtra("editMode").equalsIgnoreCase("edit")) {
                            surveyDetail.setStrEdtSkinOtherBrand("");
                            surveyDetail.setSurvBrandUsedSkin("");
                        }
                        // MyApp.realm.commitTransaction();
                        checkBoxInit(skinBrand1, R.id.skinBrand1, skinSet, null, "skin");
                        checkBoxInit(skinBrand2, R.id.skinBrand2, skinSet, null, "skin");
                        checkBoxInit(skinBrand3, R.id.skinBrand3, skinSet, null, "skin");
                        checkBoxInit(skinBrand4, R.id.skinBrand4, skinSet, null, "skin");
                        checkBoxInit(skinBrand5, R.id.skinBrand5, skinSet, null, "skin");
                        checkBoxInit(skinBrand6, R.id.skinBrand6, skinSet, null, "skin");
                        checkBoxInit(skinBrand7, R.id.skinBrand7, skinSet, null, "skin");
                        checkBoxInit(skinBrand8, R.id.skinBrand8, skinSet, null, "skin");
                        checkBoxInit(skinBrand9, R.id.skinBrand9, skinSet, null, "skin");
                        checkBoxInit(skinBrand10, R.id.skinBrand10, skinSet, null, "skin");
                        checkBoxInit(skinBrand11, R.id.skinBrand11, skinSet, null, "skin");
                        checkBoxInit(skinBrand12, R.id.skinBrand12, skinSet, null, "skin");
                        checkBoxInit(skinBrand13, R.id.skinBrand13, skinSet, null, "skin");
                        checkBoxInit(skinBrand14, R.id.skinBrand14, skinSet, null, "skin");
                        checkBoxInit(skinBrand15, R.id.skinBrand15, null, edtSkinOtherBrand, "skin");
                        edtNoOfOptSkin.setText("");
                        edtSkinOtherBrand.setText("");

                    }
                }
                break;

            case R.id.cbIsNailsCheck:

                if (isChecked) {
                    if (strCatMain.equalsIgnoreCase("Institute")) {
                        allGone(1);
                        cbIsNailsCheck.setChecked(true);
                        edtNoOfOptNails.setText("");
                        linNailsBlock.setVisibility(View.GONE);
                        removeDrawableCheckbox(R.drawable.ic_keyboard_arrow_up_black_24dp, cbIsNails);
                    }
                } else {

                    if (strCatMain.equalsIgnoreCase("Institute")) {
                        allGone(1);
                        cbIsNailsCheck.setChecked(false);
                        edtNoOfOptNails.setText("");
                        removeDrawableCheckbox(R.drawable.ic_keyboard_arrow_down_black_24dp, cbIsNails);
                    } else {
                        // MyApp.realm.beginTransaction();
                        if (getIntent().getStringExtra("editMode").equalsIgnoreCase("edit")) {
                            surveyDetail.setStrEdtNailsOtherBrand("");
                            surveyDetail.setSurvBrandUsedNails("");
                        }
                        // MyApp.realm.commitTransaction();
                        checkBoxInit(nailsBrand1, R.id.nailsBrand1, nailsSet, null, "nails");
                        checkBoxInit(nailsBrand2, R.id.nailsBrand2, nailsSet, null, "nails");
                        checkBoxInit(nailsBrand3, R.id.nailsBrand3, nailsSet, null, "nails");
                        checkBoxInit(nailsBrand4, R.id.nailsBrand4, nailsSet, null, "nails");
                        checkBoxInit(nailsBrand5, R.id.nailsBrand5, null, edtNailsOtherBrand, "nails");

                        edtNoOfOptNails.setText("");
                        edtNailsOtherBrand.setText("");

                    }
                }
                break;

            case R.id.cbIsSpaCheck:

                if (isChecked) {
                    if (strCatMain.equalsIgnoreCase("Institute")) {
                        allGone(1);
                        cbIsSpaCheck.setChecked(true);
                        edtNoOfOptSpa.setText("");
                        linSpaBlock.setVisibility(View.GONE);
                        removeDrawableCheckbox(R.drawable.ic_keyboard_arrow_up_black_24dp, cbIsSpa);
                    }

                } else {


                    if (strCatMain.equalsIgnoreCase("Institute")) {
                        allGone(1);
                        cbIsSpaCheck.setChecked(false);
                        edtNoOfOptSpa.setText("");
                        removeDrawableCheckbox(R.drawable.ic_keyboard_arrow_down_black_24dp, cbIsSpa);
                    } else {
                        // MyApp.realm.beginTransaction();
                        if (getIntent().getStringExtra("editMode").equalsIgnoreCase("edit")) {
                            surveyDetail.setStrEdtSpaOtherBrand("");
                            surveyDetail.setSurvBrandUsedSpa("");
                        }
                        // MyApp.realm.commitTransaction();
                        checkBoxInit(spaBrand1, R.id.spaBrand1, spaSet, null, "spa");
                        checkBoxInit(spaBrand2, R.id.spaBrand2, null, edtSpaOtherBrand, "spa");

                        edtNoOfOptSpa.setText("");
                        edtSpaOtherBrand.setText("");

                    }
                }
                break;
            case R.id.cbIsMakeupCheck:

                if (isChecked) {
                    if (strCatMain.equalsIgnoreCase("Institute")) {
                        allGone(1);
                        cbIsMakeupCheck.setChecked(true);
                        edtNoOfOptMakeup.setText("");
                        linMakeupBlock.setVisibility(View.GONE);
                        removeDrawableCheckbox(R.drawable.ic_keyboard_arrow_up_black_24dp, cbIsMakeup);
                    }

                } else {

                    if (strCatMain.equalsIgnoreCase("Institute")) {
                        allGone(1);
                        cbIsMakeupCheck.setChecked(false);
                        edtNoOfOptMakeup.setText("");
                        removeDrawableCheckbox(R.drawable.ic_keyboard_arrow_down_black_24dp, cbIsMakeup);
                    } else {
                        //  MyApp.realm.beginTransaction();
                        if (getIntent().getStringExtra("editMode").equalsIgnoreCase("edit")) {
                            surveyDetail.setStrEdtMakeupOtherBrand("");
                            surveyDetail.setSurvBrandUsedMakeup("");
                        }

                        // MyApp.realm.commitTransaction();
                        checkBoxInit(makeupBrand1, R.id.makeupBrand1, makeupSet, null, "makeup");
                        checkBoxInit(makeupBrand2, R.id.makeupBrand2, makeupSet, null, "makeup");
                        checkBoxInit(makeupBrand3, R.id.makeupBrand3, makeupSet, null, "makeup");
                        checkBoxInit(makeupBrand4, R.id.makeupBrand4, makeupSet, null, "makeup");
                        checkBoxInit(makeupBrand5, R.id.makeupBrand5, makeupSet, null, "makeup");
                        checkBoxInit(makeupBrand6, R.id.makeupBrand6, null, edtMakeupOtherBrand, "makeup");

                        edtNoOfOptMakeup.setText("");
                        edtMakeupOtherBrand.setText("");
                    }
                }
                break;
            case R.id.cbIsOtherServiceCheck:

                if (isChecked) {
                    linOtherServiceBlockContainer.setVisibility(View.VISIBLE);

                    if (strCatMain.equalsIgnoreCase("Institute")) {
                        allGone(1);
                        ivAddView.setVisibility(View.VISIBLE);
                        insertPoint.setVisibility(View.VISIBLE);
                        cbIsOtherServiceCheck.setChecked(true);
                        removeDrawableCheckbox(R.drawable.ic_keyboard_arrow_up_black_24dp, cbIsOtherService);
                    }

                } else {

                    linOtherServiceBlockContainer.setVisibility(View.GONE);

                    if (strCatMain.equalsIgnoreCase("Institute")) {
                        allGone(1);
                        ivAddView.setVisibility(View.GONE);
                        insertPoint.setVisibility(View.GONE);
                        cbIsOtherServiceCheck.setChecked(false);
                        removeDrawableCheckbox(R.drawable.ic_keyboard_arrow_down_black_24dp, cbIsOtherService);
                    } else {
                        //  MyApp.realm.beginTransaction();
                        if (getIntent().getStringExtra("editMode").equalsIgnoreCase("edit")) {
                            surveyDetail.setSurvOtrService("");
                        }
                        // MyApp.realm.commitTransaction();
                        linOtherServiceBlockContainer.removeAllViews();
                    }
                }
                break;

            //for both  salon and institute
            case R.id.cbIsHair:
                allGone(1);
                if (isChecked) {
                    if (strCatMain.equalsIgnoreCase("Salon")) {
                        linHairBlock.setVisibility(View.VISIBLE);
                        setDrawableCheckbox(R.drawable.ic_keyboard_arrow_up_black_24dp, cbIsHair);
                    } else if (strCatMain.equalsIgnoreCase("Institute")) {
                        linHairBlock.setVisibility(View.GONE);
                        cbIsHairCheck.setChecked(true);
                        edtNoOfOptHair.setText("");
                        removeDrawableCheckbox(R.drawable.ic_keyboard_arrow_up_black_24dp, cbIsHair);
                    }


                } else {

                    linHairBlock.setVisibility(View.GONE);
                    if (strCatMain.equalsIgnoreCase("Institute")) {
                        edtNoOfOptHair.setText("");
                        cbIsHairCheck.setChecked(false);
                        removeDrawableCheckbox(R.drawable.ic_keyboard_arrow_down_black_24dp, cbIsHair);
                    } else {
                        setDrawableCheckbox(R.drawable.ic_keyboard_arrow_down_black_24dp, cbIsHair);
                    }
                }
                break;
            case R.id.cbIsSkin:
                allGone(1);
                if (isChecked) {
                    if (strCatMain.equalsIgnoreCase("Salon")) {
                        linSkinBlock.setVisibility(View.VISIBLE);
                        setDrawableCheckbox(R.drawable.ic_keyboard_arrow_up_black_24dp, cbIsSkin);
                    } else if (strCatMain.equalsIgnoreCase("Institute")) {
                        cbIsSkinCheck.setChecked(true);
                        edtNoOfOptSkin.setText("");
                        linSkinBlock.setVisibility(View.GONE);
                        removeDrawableCheckbox(R.drawable.ic_keyboard_arrow_up_black_24dp, cbIsSkin);
                    }

                } else {
                    if (strCatMain.equalsIgnoreCase("Institute")) {
                        cbIsSkinCheck.setChecked(false);
                        edtNoOfOptSkin.setText("");
                        linSkinBlock.setVisibility(View.GONE);
                        removeDrawableCheckbox(R.drawable.ic_keyboard_arrow_down_black_24dp, cbIsSkin);
                    } else {
                        setDrawableCheckbox(R.drawable.ic_keyboard_arrow_down_black_24dp, cbIsSkin);
                    }
                }
                break;

            case R.id.cbIsNails:
                allGone(1);
                if (isChecked) {
                    if (strCatMain.equalsIgnoreCase("Salon")) {
                        linNailsBlock.setVisibility(View.VISIBLE);
                        setDrawableCheckbox(R.drawable.ic_keyboard_arrow_up_black_24dp, cbIsNails);
                    } else if (strCatMain.equalsIgnoreCase("Institute")) {
                        cbIsNailsCheck.setChecked(true);
                        edtNoOfOptNails.setText("");
                        linNailsBlock.setVisibility(View.GONE);
                        removeDrawableCheckbox(R.drawable.ic_keyboard_arrow_up_black_24dp, cbIsNails);
                    }
                } else {

                    linNailsBlock.setVisibility(View.GONE);
                    if (strCatMain.equalsIgnoreCase("Institute")) {
                        cbIsNailsCheck.setChecked(false);
                        edtNoOfOptNails.setText("");
                        removeDrawableCheckbox(R.drawable.ic_keyboard_arrow_down_black_24dp, cbIsNails);
                    } else {
                        setDrawableCheckbox(R.drawable.ic_keyboard_arrow_down_black_24dp, cbIsNails);
                    }
                }
                break;

            case R.id.cbIsSpa:
                allGone(1);
                if (isChecked) {
                    if (strCatMain.equalsIgnoreCase("Salon")) {
                        linSpaBlock.setVisibility(View.VISIBLE);
                        setDrawableCheckbox(R.drawable.ic_keyboard_arrow_up_black_24dp, cbIsSpa);
                    } else if (strCatMain.equalsIgnoreCase("Institute")) {
                        cbIsSpaCheck.setChecked(true);
                        edtNoOfOptSpa.setText("");
                        linSpaBlock.setVisibility(View.GONE);
                        removeDrawableCheckbox(R.drawable.ic_keyboard_arrow_up_black_24dp, cbIsSpa);
                    }

                } else {

                    linSpaBlock.setVisibility(View.GONE);
                    if (strCatMain.equalsIgnoreCase("Institute")) {
                        cbIsSpaCheck.setChecked(false);
                        edtNoOfOptSpa.setText("");
                        removeDrawableCheckbox(R.drawable.ic_keyboard_arrow_down_black_24dp, cbIsSpa);
                    } else {
                        setDrawableCheckbox(R.drawable.ic_keyboard_arrow_down_black_24dp, cbIsSpa);
                    }
                }
                break;
            case R.id.cbIsMakeup:
                allGone(1);
                if (isChecked) {
                    if (strCatMain.equalsIgnoreCase("Salon")) {
                        linMakeupBlock.setVisibility(View.VISIBLE);
                        setDrawableCheckbox(R.drawable.ic_keyboard_arrow_up_black_24dp, cbIsMakeup);
                    } else if (strCatMain.equalsIgnoreCase("Institute")) {
                        cbIsMakeupCheck.setChecked(true);
                        edtNoOfOptMakeup.setText("");
                        linMakeupBlock.setVisibility(View.GONE);
                        removeDrawableCheckbox(R.drawable.ic_keyboard_arrow_up_black_24dp, cbIsMakeup);
                    }

                } else {

                    linMakeupBlock.setVisibility(View.GONE);
                    if (strCatMain.equalsIgnoreCase("Institute")) {
                        cbIsMakeupCheck.setChecked(false);
                        edtNoOfOptMakeup.setText("");
                        removeDrawableCheckbox(R.drawable.ic_keyboard_arrow_down_black_24dp, cbIsMakeup);
                    } else {
                        setDrawableCheckbox(R.drawable.ic_keyboard_arrow_down_black_24dp, cbIsMakeup);
                    }
                }
                break;
            case R.id.cbIsOtherService:
                allGone(1);
                if (isChecked) {
                    linOtherServiceBlockContainer.setVisibility(View.VISIBLE);
                    ivAddView.setVisibility(View.VISIBLE);
                    if (strCatMain.equalsIgnoreCase("Institute")) {
                        cbIsOtherServiceCheck.setChecked(true);
                        removeDrawableCheckbox(R.drawable.ic_keyboard_arrow_up_black_24dp, cbIsOtherService);
                    } else {
                        setDrawableCheckbox(R.drawable.ic_keyboard_arrow_up_black_24dp, cbIsOtherService);
                    }

                } else {

                    linOtherServiceBlockContainer.setVisibility(View.GONE);
                    ivAddView.setVisibility(View.GONE);
                    if (strCatMain.equalsIgnoreCase("Institute")) {
                        cbIsOtherServiceCheck.setChecked(false);
                        removeDrawableCheckbox(R.drawable.ic_keyboard_arrow_down_black_24dp, cbIsOtherService);
                    } else {
                        setDrawableCheckbox(R.drawable.ic_keyboard_arrow_down_black_24dp, cbIsOtherService);
                    }
                }
                break;


            //for main titles and blocks
            case R.id.tvSalonDetails:
                //allGone(0);
                if (isChecked) {
                    allGone(0);
                    tvSalonDetails.setChecked(true);
                    cvSecond.setVisibility(View.VISIBLE);
                    setDrawableTextView(R.drawable.ic_keyboard_arrow_up_black_24dp, tvSalonDetails);

                } else {
                    tvSalonDetails.setChecked(false);
                    cvSecond.setVisibility(View.GONE);
                    setDrawableTextView(R.drawable.ic_keyboard_arrow_down_black_24dp, tvSalonDetails);
                }
                break;
            case R.id.tvSocialMediaLbl:
                // allGone(0);
                if (isChecked) {
                    allGone(0);
                    tvSocialMediaLbl.setChecked(true);
                    cvSocMed.setVisibility(View.VISIBLE);
                    setDrawableCheckbox(R.drawable.ic_keyboard_arrow_up_black_24dp, tvSocialMediaLbl);
                } else {
                    tvSocialMediaLbl.setChecked(false);
                    cvSocMed.setVisibility(View.GONE);
                    setDrawableCheckbox(R.drawable.ic_keyboard_arrow_down_black_24dp, tvSocialMediaLbl);
                }
                break;

            case R.id.tvOwnerDetails:
                //allGone(0);
                if (isChecked) {
                    allGone(0);
                    tvOwnerDetails.setChecked(true);
                    cvFourth.setVisibility(View.VISIBLE);
                    setDrawableCheckbox(R.drawable.ic_keyboard_arrow_up_black_24dp, tvOwnerDetails);
                } else {
                    tvOwnerDetails.setChecked(false);
                    cvFourth.setVisibility(View.GONE);
                    setDrawableCheckbox(R.drawable.ic_keyboard_arrow_down_black_24dp, tvOwnerDetails);
                }
                break;

            case R.id.tvSerOffDetails:
                // allGone(1);
                if (isChecked) {
                    allGone(1);
                    tvSerOffDetails.setChecked(true);
                    cvEighth.setVisibility(View.VISIBLE);
                    setDrawableCheckbox(R.drawable.ic_keyboard_arrow_up_black_24dp, tvSerOffDetails);
                } else {
                    tvSerOffDetails.setChecked(false);
                    cvEighth.setVisibility(View.GONE);
                    setDrawableCheckbox(R.drawable.ic_keyboard_arrow_down_black_24dp, tvSerOffDetails);
                }
                break;
            case R.id.tvInfraStDetails:
                //allGone(0);
                if (isChecked) {
                    allGone(0);
                    tvInfraStDetails.setChecked(true);
                    cvTenth.setVisibility(View.VISIBLE);
                    setDrawableCheckbox(R.drawable.ic_keyboard_arrow_up_black_24dp, tvInfraStDetails);
                } else {
                    tvInfraStDetails.setChecked(false);
                    cvTenth.setVisibility(View.GONE);
                    setDrawableCheckbox(R.drawable.ic_keyboard_arrow_down_black_24dp, tvInfraStDetails);
                }
                break;

        }

    }

    public void showYearDialog() {


        MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(ExpressInterestActivity.this, new MonthPickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(int selectedMonth, int selectedYear) {
                tvEstYear.setText(Integer.toString(selectedYear));
                choosenYear = selectedYear;
            }
        }, choosenYear, 0);

        builder.showYearOnly()
                .setYearRange(1900, currentYear)
                .build()
                .show();


    }

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imagePicker.handleActivityResult(resultCode, requestCode, data);

//        byte[] bytes=Utils.getCompressedFile(file);  //required while online update or sync data
    }*/

    private void tiemPicker(final String which) {
        // Get Current Time
        if (which.equalsIgnoreCase("from")) {
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);
        }

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (which.equalsIgnoreCase("to")) {
                            if (hourOfDay > mHour) {
                                mHour = hourOfDay;
                                mMinute = minute;
                            } else {
                                showFailToast(ExpressInterestActivity.this, getResources().getString(R.string.time_selection_err));
                            }
                        } else if (which.equalsIgnoreCase("from")) {
                            mHour = hourOfDay;
                            mMinute = minute;
                        }
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.set(0, 0, 0, mHour, mMinute);

                        if (which.equalsIgnoreCase("from")) {
                            tvFromTime.setText(simpleDateFormat.format(calendar1.getTimeInMillis()));
                        } else {
                            tvToTime.setText(simpleDateFormat.format(calendar1.getTimeInMillis()));
                        }
                    }
                }, mHour, mMinute, true);
        timePickerDialog.show();
    }

    public void addView() //Images add dynamic
    {
        LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(R.layout.image_picker_layout, null);


// fill in any details dynamically here
        final ImageView salonImg = (ImageView) v.findViewById(R.id.salonImg);

// insert into main view
        LinearLayout insertPoint = (LinearLayout) findViewById(R.id.imageContainer);
        insertPoint.addView(v);
        //insertPoint.addView(v, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));

        salonImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onProfileImageClick(salonImg);

            }
        });

    }

    public Bitmap getBitmap(String filePath) {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File image = new File(filePath);
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(), bmOptions);
            bitmap = Bitmap.createScaledBitmap(bitmap, 150, 120, true);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }

    public void addViewOtherServices() //Dynamic add other services view
    {
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.add_other_services_layout, null, false);

// insert into main view
        insertPoint = findViewById(R.id.linOtherServiceBlockContainer);
        if (strCatMain.equalsIgnoreCase("Institute")) {
            ((EditText) (v.findViewById(R.id.otherServiceNoofOpt))).setVisibility(View.GONE);
            ((EditText) (v.findViewById(R.id.otherSerOtherBrand))).setVisibility(View.GONE);
        }
        insertPoint.addView(v);
        ivAddView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addViewOtherServices();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {

        }/*else {
            imagePicker.handlePermission(requestCode, grantResults);
        }*/
    }

    public void setDrawableCheckbox(int id, CheckBox checkBox) {
        Drawable img = getResources().getDrawable(id);
        img.setBounds(0, 0, 70, 70);
        checkBox.setCompoundDrawables(null, null, img, null);
    }

    //remove drawable
    public void removeDrawableCheckbox(int id, CheckBox checkBox) {
        Drawable img = getResources().getDrawable(id);
        img.setBounds(0, 0, 70, 70);
        checkBox.setCompoundDrawables(null, null, null, null);
    }

    private void setDrawableTextView(int ic_keyboard_arrow_up_black_24dp, CheckBox tvSalonDetails) {
        Drawable img = getResources().getDrawable(ic_keyboard_arrow_up_black_24dp);
        img.setBounds(0, 0, 70, 70);
        tvSalonDetails.setCompoundDrawables(null, null, img, null);
    }

  /*  @Override
    public void onLocationChanged(Location location) {
        latitude = "" + location.getLatitude();
        longitude = "" + location.getLongitude();
        Log.i("LagLong", latitude + " " + longitude);
    }*/

    public void loadJsonStateCity() {
        Set<String> states = new HashSet<>();
        //location city state
        ArrayList<String> statesNames = new ArrayList<>();
        //   spState.setTitle("Select State");
        //   spCity.setTitle("Select City");
        int statePos = 0;
        final int[] cityPos = {0};
        try {

            Gson gson = new Gson();
            stateCityBean = gson.fromJson(Utils.loadJSONFromAsset(ExpressInterestActivity.this), StateCityBean.class);
            for (int i = 0; i < stateCityBean.getCities().size(); i++) {
                StateCityBean.City city = stateCityBean.getCities().get(i);
                Log.i("Details-->", city.getState());

                states.add(city.getState());

            }
            statesNames = new ArrayList<>(states);
            //statesNames.add(0, "Select State");
            if (getIntent().getStringExtra("editMode").equalsIgnoreCase("edit")) {
                List<String> cityname = new ArrayList<>();
                statePos = statesNames.indexOf(surveyDetail.getSurvState());
                strState = surveyDetail.getSurvState();
                for (int i = 0; i < stateCityBean.getCities().size(); i++) {
                    StateCityBean.City city = stateCityBean.getCities().get(i);
                    if (strState.equalsIgnoreCase(city.getState())) {
                        Log.i("Details-->", city.getState());

                        cityname.add(city.getName());
                        if (getIntent().getStringExtra("editMode").equalsIgnoreCase("edit")) {
                            if (surveyDetail.getSurvCity().equalsIgnoreCase(city.getName())) {
                                cityPos[0] = cityname.indexOf(surveyDetail.getSurvCity());
                                strCity = surveyDetail.getSurvCity();
                            }
                        }
                    }
                }
                // cityname.add(0, "Select City");
                spCity.setAdapter(new ArrayAdapter<String>(ExpressInterestActivity.this, android.R.layout.simple_list_item_1, cityname));
                // spCity.setSelection(cityPos[0]);
                spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                       /* if (position == 0) {
                        } else {*/
                        strCity = spCity.getSelectedItem().toString();
                        //}
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                spState.setAdapter(new ArrayAdapter<String>(ExpressInterestActivity.this, android.R.layout.simple_list_item_1, statesNames));
                // spState.setSelection(statePos);


            } else {
                spState.setAdapter(new ArrayAdapter<String>(ExpressInterestActivity.this, android.R.layout.simple_list_item_1, statesNames));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        // spState.setSelection(statePos);
        spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                List<String> cityname = new ArrayList<>();
               /* if (position == 0) {
                } else {*/
                strState = spState.getSelectedItem().toString();
                for (int i = 0; i < stateCityBean.getCities().size(); i++) {
                    StateCityBean.City city = stateCityBean.getCities().get(i);
                    if (spState.getSelectedItem().toString().equalsIgnoreCase(city.getState())) {
                        Log.i("Details-->", city.getName());

                        cityname.add(city.getName());
                        if (getIntent().getStringExtra("editMode").equalsIgnoreCase("edit")) {
                            if (surveyDetail.getSurvCity().equalsIgnoreCase(city.getName())) {
                                cityPos[0] = cityname.indexOf(surveyDetail.getSurvCity());
                                strCity = surveyDetail.getSurvCity();
                            }
                        }
                    }
                }
                // cityname.add(0, "Select City");
                spCity.setAdapter(new ArrayAdapter<String>(ExpressInterestActivity.this, android.R.layout.simple_list_item_1, cityname));
                //spCity.setSelection(cityPos[0]);
                spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                           /* if (position == 0) {
                            } else {*/
                        strCity = spCity.getSelectedItem().toString();
                        //}
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                //}
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    // my button click function
    void onProfileImageClick(final ImageView salonImg) {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            showImagePickerOptions(salonImg);
                        } else {
                            // TODO - handle permission denied case
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<com.karumi.dexter.listener.PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }

                   /* @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                    }*/  // TODO for design
                }).check();
    }

    private void showImagePickerOptions(ImageView salonImg) {
        ImagePickerActivity.showImagePickerOptions(this, new ImagePickerActivity.PickerOptionListener() {
            @Override
            public void onTakeCameraSelected() {
                launchCameraIntent();
            }

            @Override
            public void onChooseGallerySelected() {
                launchGalleryIntent();
            }
        });
    }

    private void launchCameraIntent() {
        Intent intent = new Intent(ExpressInterestActivity.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 10); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 8);

        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 800);

        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void launchGalleryIntent() {
        Intent intent = new Intent(ExpressInterestActivity.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 10); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 8);
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                try {
                    // You can update this bitmap to your server
                    //Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    File file = new File(uri.getPath());
                    String newPath = Environment.getExternalStorageDirectory() + "/surveyimg/" + file.getName();
                    Log.i("imagepath", "img path " + file.getAbsolutePath() + " \n" + file.getPath());
                    if (imgArr.size() < 5) {
                        /*try {
                            copyFile(file, new File(newPath));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }*/
                        imgArr.add(uri.getPath());
                        LinearLayout insertPoint = (LinearLayout) findViewById(R.id.imageContainer);
                        insertPoint.removeAllViews();
                        for (int i = 0; i < imgArr.size(); i++) {
                            LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View v = vi.inflate(R.layout.image_picker_layout, null);
                            insertPoint = (LinearLayout) findViewById(R.id.imageContainer);
                            ImageView salonImg = (ImageView) v.findViewById(R.id.salonImg);
                            insertPoint.addView(v);
                            Bitmap bitmap = getBitmap(imgArr.get(i));
                            if (bitmap != null) {
                                salonImg.setImageBitmap(bitmap);
                            }
                        }
                        addView();
                    } else {
                        showFailToast(ExpressInterestActivity.this, getResources().getString(R.string.select_five_images_msg));
                    }
                    // loading profile image from local cache
                    //loadProfile(uri.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }else if(requestCode==resultCodeChangePass)
        {
            startActivity(new Intent(ExpressInterestActivity.this, LoginActivity.class));
            finish();
        }
    }

    private void addExpressInterest(SurveyDetail addSalonBeanObj) {

        ApiInterface service = RetrofitInstance.getClient().create(ApiInterface.class);
        final Dialog progressDialog = showProgress(ExpressInterestActivity.this, 0);
        progressDialog.show();
        MultipartBody.Part[] multiPartImg = getImageBody(addSalonBeanObj.getSurvImg());

        service.addExpressInterest(getOtherFields(userId),
                getOtherFields("" + addSalonBeanObj.getSurvLocalId()),
                getOtherFields(addSalonBeanObj.getSurvName()),
                getOtherFields(addSalonBeanObj.getSurvManName()),
                getOtherFields(addSalonBeanObj.getSurvAddr()),
                getOtherFields(addSalonBeanObj.getSurvState()),
                getOtherFields(addSalonBeanObj.getSurvCity()),
                getOtherFields(addSalonBeanObj.getSurvPinCode()),
                getOtherFields(addSalonBeanObj.getSurvEstblishYear()),
                getOtherFields(addSalonBeanObj.getSurvLat()),
                getOtherFields(addSalonBeanObj.getSurvLon()),
                getOtherFields(addSalonBeanObj.getSurvStartTime()),
                getOtherFields(addSalonBeanObj.getSurvEndTime()),
                getOtherFields(addSalonBeanObj.getSurvCatMain()),
                getOtherFields(addSalonBeanObj.getSurvWorkHrs()),
                getOtherFields(addSalonBeanObj.getSurvCertification()),
                getOtherFields(addSalonBeanObj.getSurvWebsite()),
                getOtherFields(addSalonBeanObj.getSurvEmail()),
                getOtherFields(addSalonBeanObj.getSurvFacebook()),
                getOtherFields(addSalonBeanObj.getSurvInstagram()),
                getOtherFields(addSalonBeanObj.getSurvSalNumber()),
                getOtherFields(addSalonBeanObj.getSurvGst()),
                getOtherFields(addSalonBeanObj.getSurvGstNo()),
                getOtherFields(addSalonBeanObj.getSurvRateOfGst()),
                getOtherFields(addSalonBeanObj.getSurvOwnerName()),
                getOtherFields(addSalonBeanObj.getSurvOwnNumber()),
                getOtherFields(addSalonBeanObj.getSurvOwnEdu()),
                getOtherFields(addSalonBeanObj.getSurvManNumber()),
                getOtherFields(addSalonBeanObj.getSurvResParking()),
                getOtherFields(addSalonBeanObj.getSurvTv()),
                getOtherFields(addSalonBeanObj.getSurvFreeWifi()),
                getOtherFields(addSalonBeanObj.getSurvAc()),
                getOtherFields(addSalonBeanObj.getSurvHouseStafCount()),
                getOtherFields(addSalonBeanObj.getSurvMgmtStaffCount()),
                getOtherFields(addSalonBeanObj.getSurvHairSkinChairs()),
                getOtherFields(addSalonBeanObj.getSurvNoHair()),
                getOtherFields(addSalonBeanObj.getSurvNoSkin()),
                getOtherFields(addSalonBeanObj.getSurvNoNails()),
                getOtherFields(addSalonBeanObj.getSurvNoSpa()),
                getOtherFields(addSalonBeanObj.getSurvNoMakeup()),
                getOtherFields(addSalonBeanObj.getSurvBrandUsedHair()),
                getOtherFields(addSalonBeanObj.getSurvBrandUsedSkin()),
                getOtherFields(addSalonBeanObj.getSurvBrandUsedNails()),
                getOtherFields(addSalonBeanObj.getSurvBrandUsedSpa()),
                getOtherFields(addSalonBeanObj.getSurvBrandUsedMakeup()),
                getOtherFields(addSalonBeanObj.getSurvOtrService()),
                getOtherFields(addSalonBeanObj.getSurvOtrOpt()),
                getOtherFields(addSalonBeanObj.getSurvOtrBrandUsed()),
                getOtherFields(addSalonBeanObj.getSurvStatus()),
                getOtherFields(addSalonBeanObj.getSurvId()),
                getOtherFields(addSalonBeanObj.getStrEdtHairOtherBrand()),
                getOtherFields(addSalonBeanObj.getStrEdtSkinOtherBrand()),
                getOtherFields(addSalonBeanObj.getStrEdtNailsOtherBrand()),
                getOtherFields(addSalonBeanObj.getStrEdtSpaOtherBrand()),
                getOtherFields(addSalonBeanObj.getStrEdtMakeupOtherBrand()),
                getOtherFields("2"),
                multiPartImg
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ExpressInterestBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ExpressInterestBean loginBeanObj) {
                        loginBean = loginBeanObj;
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        showFailToast(ExpressInterestActivity.this,  getResources().getString(R.string.went_wrong));
                    }

                    @Override
                    public void onComplete() {
                        progressDialog.dismiss();
                        // Updates UI with data
                        if (loginBean.getStatus() == 1) {
                            startActivityForResult(new Intent(ExpressInterestActivity.this, SuccessDialogActivity.class).putExtra("msg","Your request has been submitted successfully, system admin will get back to you shortly"),resultCodeChangePass);

                        } else {
                            showFailToast(ExpressInterestActivity.this,  "" + loginBean.getMsg());
                        }
//                        cPresenter.updateCoinList(allCurrencyList);
                    }
                });


    }

    /*private void getActInactStatus() {

        ApiInterface service = RetrofitInstance.getClient().create(ApiInterface.class);


        service.getActInactStatus(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ActiveInactiveBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ActiveInactiveBean loginBeanObj) {
                        activeInactiveBean = loginBeanObj;
                    }

                    @Override
                    public void onError(Throwable e) {
                        //progressDialog.dismiss();
                        // Toast.makeText(SalonLeadsListActivity.this,getResources().getString(R.string.went_wrong), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        // Updates UI with data
                        // progressDialog.dismiss();
                        if (activeInactiveBean.getStatus() == 1) {
                            List<ActiveInactiveBean.Result> result = activeInactiveBean.getResult();

                            if (result.get(0).getUserStatus().equalsIgnoreCase("0")) {
                                getClearPrefs(ExpressInterestActivity.this);
                                startActivity(new Intent(ExpressInterestActivity.this, LoginActivity.class));
                                finish();
                            }


                        } else {
                            //Toast.makeText(SalonLeadsListActivity.this,""+activeInactiveBean.getMsg(), Toast.LENGTH_SHORT).show();
                        }
//                        cPresenter.updateCoinList(allCurrencyList);
                    }
                });


    }*/

    @Override
    protected void onResume() {
        super.onResume();
      /*  if (isNetworkAvailable(ExpressInterestActivity.this)) {

            getActInactStatus();
        } else {
            //Toast.makeText(SalonLeadsListActivity.this, getResources().getString(R.string.please_check_newtork_connection), Toast.LENGTH_SHORT).show();

        }*/

    }


}
