package com.prometteur.cpapp.retrofit;


import com.prometteur.cpapp.beans.AdvertisementBean;
import com.prometteur.cpapp.beans.BrandListBean;
import com.prometteur.cpapp.beans.CancelAppBean;
import com.prometteur.cpapp.beans.CheckPenaltyBean;
import com.prometteur.cpapp.beans.ComboListBean;
import com.prometteur.cpapp.beans.ExpressInterestBean;
import com.prometteur.cpapp.beans.ForgotPassBean;
import com.prometteur.cpapp.beans.InvoiceBean;
import com.prometteur.cpapp.beans.LoginBean;
import com.prometteur.cpapp.beans.NotificationBean;
import com.prometteur.cpapp.beans.OfflineStatusBean;
import com.prometteur.cpapp.beans.OngoingBean;
import com.prometteur.cpapp.beans.OtpBean;
import com.prometteur.cpapp.beans.PerformanceBean;
import com.prometteur.cpapp.beans.ProfileBean;
import com.prometteur.cpapp.beans.PromoOfferBean;
import com.prometteur.cpapp.beans.RateCardBean;
import com.prometteur.cpapp.beans.ReschduleGetDataBean;
import com.prometteur.cpapp.beans.ReviewBean;
import com.prometteur.cpapp.beans.StatisticsBean;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {


    String BASE_URL = "http://survey.sairoses.com/cp/index.php/api/";
//    String BASE_URL = "https://mooi.app/cp/index.php/api/";

  /*  @FormUrlEncoded
    @POST("razorpay/transfer_order.php") //TODO api change and Bean class change
    Observable<OrderIdBean> getOrderId(
            @Field("currency") String currency,
            @Field("amount") int amount,
            @Field("payment_capture") int paymentCapture,


            @Field("taccount") String taccount,
            @Field("tcurrency") String tcurrency,
            @Field("tamount") int tamount,

            @Field("t1account") String t1account,
            @Field("t1currency") String t1currency,
            @Field("t1amount") int t1amount
    );*/

    @Multipart
    @POST("mAdd/express_interest")//completed survey adding
    Observable<ExpressInterestBean> addExpressInterest(@Part("surv_user_id") RequestBody userId,
                                                       @Part("local_id") RequestBody localId,
                                                       @Part("surv_name") RequestBody otherFields,
                                                       @Part("surv_man_name") RequestBody otherFields1,
                                                       @Part("surv_addr") RequestBody otherFields2,
                                                       @Part("surv_state") RequestBody otherFields3,
                                                       @Part("surv_city") RequestBody otherFields4,
                                                       @Part("surv_pin_code") RequestBody otherFields5,
                                                       @Part("surv_estblish_year") RequestBody otherFields6,
                                                       @Part("surv_lat") RequestBody otherFields7,
                                                       @Part("surv_lon") RequestBody otherFields8,
                                                       @Part("surv_start_time") RequestBody otherFields9,
                                                       @Part("surv_end_time") RequestBody otherFields10,
                                                       @Part("surv_slon_cat") RequestBody otherFields11, //surv_slon_cat changed to surv_cat_main
                                                       @Part("surv_work_hrs") RequestBody otherFields12,
                                                       @Part("surv_certification") RequestBody otherFields13,
                                                       @Part("surv_website") RequestBody otherFields14,
                                                       @Part("surv_email") RequestBody otherFields15,
                                                       @Part("surv_facebook") RequestBody otherFields16,
                                                       @Part("surv_instagram") RequestBody otherFields17,
                                                       @Part("surv_sal_number") RequestBody otherFields18,
                                                       @Part("surv_gst") RequestBody otherFields19,
                                                       @Part("surv_gst_no") RequestBody otherFields20,
                                                       @Part("surv_rate_of_gst") RequestBody otherFields21,
                                                       @Part("surv_owner_name") RequestBody otherFields22,
                                                       @Part("surv_own_number") RequestBody otherFields23,
                                                       @Part("surv_own_edu") RequestBody otherFields24,
                                                       @Part("surv_man_number") RequestBody otherFields25,
                                                       @Part("surv_res_parking") RequestBody otherFields26,
                                                       @Part("surv_tv") RequestBody otherFields27,
                                                       @Part("surv_free_wifi") RequestBody otherFields28,
                                                       @Part("surv_ac") RequestBody otherFields29,
                                                       @Part("surv_house_staf_count") RequestBody otherFields30,
                                                       @Part("surv_mgmt_staff_count") RequestBody otherFields31,
                                                       @Part("surv_hair_skin_chairs") RequestBody otherFields32,
                                                       @Part("surv_no_hair") RequestBody otherFields33,
                                                       @Part("surv_no_skin") RequestBody otherFields34,
                                                       @Part("surv_no_nails") RequestBody otherFields35,
                                                       @Part("surv_no_spa") RequestBody otherFields36,
                                                       @Part("surv_no_makeup") RequestBody otherFields37,
                                                       @Part("surv_brand_used_hair") RequestBody otherFields38,
                                                       @Part("surv_brand_used_skin") RequestBody otherFields39,
                                                       @Part("surv_brand_used_nails") RequestBody otherFields40,
                                                       @Part("surv_brand_used_spa") RequestBody otherFields41,
                                                       @Part("surv_brand_used_makeup") RequestBody otherFields42,
                                                       @Part("surv_otr_service") RequestBody otherFields43,
                                                       @Part("surv_otr_opt") RequestBody otherFields44,
                                                       @Part("surv_otr_brand_used") RequestBody otherFields45,
                                                       @Part("surv_status") RequestBody otherFields46,
                                                       @Part("surv_id") RequestBody otherFields47,
                                                       @Part("surv_brand_othr_hair") RequestBody otherFields48,
                                                       @Part("surv_brand_othr_skin") RequestBody otherFields49,
                                                       @Part("surv_brand_othr_nails") RequestBody otherFields50,
                                                       @Part("surv_brand_othr_spa") RequestBody otherFields51,
                                                       @Part("surv_brand_othr_makeup") RequestBody otherFields52,
                                                       @Part("surv_creator") RequestBody otherFields53,
                                                       @Part MultipartBody.Part[] otherFields54
    );

    @FormUrlEncoded
    @POST("mLogin")
    Observable<LoginBean> getLogin(@Field("user_name") String userName,
                                   @Field("user_password") String userPassword,
                                   @Field("user_fcm_key") String userFcmKey);

    @FormUrlEncoded
    @POST("mChangePassword")
    Observable<LoginBean> getChangePass(@Field("user_email") String userEmail,
                                        @Field("user_mb_no") String userMbNo,
                                        @Field("user_password") String userPassword);

    @FormUrlEncoded
    @POST("mForgot")
    Observable<ForgotPassBean> getForgotPass(@Field("user_email") String userEmail,
                                             @Field("user_mb_no") String userMob);

    @FormUrlEncoded
    @POST("fields/ongoing_appoinment")
    Observable<OngoingBean> getOngoingAppointment(@Field("branch_id") String branchId);

    @FormUrlEncoded
    @POST("fields/todays_appoinment")
    Observable<OngoingBean> getTodaysAppointment(@Field("branch_id") String branchId);

    @FormUrlEncoded
    @POST("fields/upcoming_appoinment")
    Observable<OngoingBean> getUpcomingAppointment(@Field("branch_id") String branchId);

    //for active
    @FormUrlEncoded
    @POST("mAdd/salon_status")
    Observable<OfflineStatusBean> updateActiveInactiveStatus(@Field("bran_id") String branchId, @Field("hours") String hours);

    //for inactive
    @FormUrlEncoded
    @POST("mAdd/salon_status")
    Observable<OfflineStatusBean> updateActiveInactiveStatus(@Field("bran_id") String branchId);

    @FormUrlEncoded
    @POST("fields/get_reschedule_details")
    Observable<ReschduleGetDataBean> getRescheduleGetData(@Field("appoinment_id") String appoinmentId,
                                                          @Field("day") String day);

    @FormUrlEncoded
    @POST("fields/appoinment_details")//for appointment list of requests
    Observable<OngoingBean> getAppointmentRequests(@Field("branch_id") String branchId,
                                                   @Field("noti_id") String notiId);

    @FormUrlEncoded
    @POST("fields/appoinment_details")//for appointment list of requests
    Observable<OngoingBean> readNotification(@Field("noti_id") String notiId);

    @FormUrlEncoded
    @POST("fields/appoinment_history")
    Observable<OngoingBean> getAppointmentHistory(@Field("branch_id") String branchId,
                                                  @Field("interval") String interval);

    @FormUrlEncoded
    @POST("mAdd/update_appoinment_status")
    Observable<OfflineStatusBean> updateAcceptRejectStatus(@Field("apt_id") String appId,
                                                           @Field("apt_status") String aptStatus,
                                                           @Field("apt_reject_reason") String reson
    );
    @FormUrlEncoded
    @POST("mAdd/update_appoinment_status")
    Observable<OfflineStatusBean> updateAcceptRejectStatusForUnattended(@Field("apt_id") String appId,
                                                           @Field("apt_reject_reason") String reson,
                                                           @Field("apt_mark_as_unattended") String aptMarkAsUnattended
    );

    @FormUrlEncoded
    @POST("mAdd/reschedule_appoinment")
    Observable<OfflineStatusBean> rescheduleAppointment(@Field("apt_id") String appId,
                                                        @Field("apt_date") String aptDate,
                                                        @Field("apt_time") String aptTime,
                                                        @Field("apt_services") String aptServices,
                                                        @Field("date_change") boolean dateChange,
                                                        @Field("time_change") boolean timeChange,
                                                        @Field("operator_change") boolean operatorChange
    );

    @FormUrlEncoded
    @POST("fields/appoinment_details")//for history details
    Observable<OngoingBean> getHistoryDetails(@Field("appoinment_id") String appId);

    @FormUrlEncoded
    @POST("sendOtp")
    Observable<OtpBean> getOTP(@Field("user_mb_no") String userMbNo,
                               @Field("text_message") String textMessage,
                               @Field("salon_name") String salonName,
                               @Field("apt_id") String aptId
    );

    @FormUrlEncoded
    @POST("fields/rate_card")
    Observable<RateCardBean> getRateCardDetails(@Field("branch_id") String branchId);

    @FormUrlEncoded
    @POST("fields/combo_offer")
    Observable<ComboListBean> getComboListDetails(@Field("branch_id") String branchId);

    @FormUrlEncoded
    @POST("fields/promotional_offer")
    Observable<PromoOfferBean> getPromoOfferDetails(@Field("branch_id") String branchId);

    @FormUrlEncoded
    @POST("fields/profile")
    Observable<ProfileBean> getProfileDetails(@Field("branch_id") String branchId,
                                              @Field("noti_id") String notiId);

    @FormUrlEncoded
    @POST("fields/brands")
    Observable<BrandListBean> getBrands(@Field("branch_id") String branchId);

    @FormUrlEncoded
    @POST("fields/invoice")
    Observable<InvoiceBean> getInvoices(@Field("branch_id") String branchId);

    @FormUrlEncoded
    @POST("fields/notification")
    Observable<NotificationBean> getNotifications(@Field("branch_id") String branchId,
                                                  @Field("cp_user_id") String userId);

    @FormUrlEncoded
    @POST("fields/advertisement")
    Observable<AdvertisementBean> getAdvertisement(@Field("pincode") String pincode,
    @Field("branch_id") String branchId);

    @FormUrlEncoded
    @POST("fields/performance")
    Observable<PerformanceBean> getPerformance(
            @Field("branch_id") String branchId,
            @Field("week_start_date") String weekStartDate,
            @Field("week_end_date") String weekEndDate);

    @FormUrlEncoded
    @POST("fields/statistics")
    Observable<StatisticsBean> getStatistics(
            @Field("branch_id") String branchId,
            @Field("week_start_date") String weekStartDate,
            @Field("week_end_date") String weekEndDate,
            @Field("pre_week_start_date") String preWeekStartDate,
            @Field("pre_week_end_date") String preWeekEndDate);

    @FormUrlEncoded
    @POST("mAdd/review")
    Observable<ReviewBean> setReviews(@Field("urate_user_id") String endUserId,
                                      @Field("urate_rating") String rating,
                                      @Field("urate_review") String review,
                                      @Field("urate_cpuser_id") String userId
    );

    @FormUrlEncoded
    @POST("fields/check_penality")
    Observable<CheckPenaltyBean> getCheckPenalty(@Field("apt_id") String aptId
    );

    @FormUrlEncoded
    @POST("mAdd/update_appoinment_status")
    Observable<CancelAppBean> getAppointmentCancel(
            @Field("apt_id") String aptId,
            @Field("apt_status") String aptStatus,
            @Field("apt_reject_reason") String aptRejectReason,
            @Field("cp_apt_penalty") String penalityAmt
    );
}