package com.example.eventhostmodule.navigation

sealed class Screen(val route: String) {

    // 🔹 Splash & Onboarding
    object Splash : Screen("splash")
    // 🔹 Onboarding (3 screens)
    object Onboarding1 : Screen("onboarding1")
    object Onboarding2 : Screen("onboarding2")
    object Onboarding3 : Screen("onboarding3")

    object Loader : Screen("loader")

    // 🔹 Auth Flow
    object Login : Screen("login")
    object OtpVerification : Screen("otp_verification")

    // 🔹 Role Selection (keep minimal)
    object RoleSelection : Screen("role_selection")

    // 🔹 EVENT HOST FLOW (YOUR MAIN PART)
    object HostHome : Screen("host_home")
    object CompleteProfileIntro : Screen("complete_profile_intro")
    object CompleteProfile : Screen("complete_profile")
    object CompleteProfilePhoto : Screen("complete_profile_photo")
    object ProfileSuccess : Screen("profile_success")

    object KycAadhaar : Screen("kyc_aadhaar")
    object KycPan : Screen("kyc_pan")
    object KycPayment : Screen("kyc_payment")
    object Chat : Screen("chat")
    object Wallet : Screen("wallet")
    object Profile : Screen("profile")
    object EventStep1 : Screen("event_step1")
    object EventFlow : Screen("event_flow")
    object EventStep2 : Screen("event_step2")
    object EventStep3 : Screen("event_step3")
    object EventStep4 : Screen("event_step4")
    object EventStep5 : Screen("event_step5")
    object EventConfirm : Screen("event_confirm")
    // 🔹 (Optional - add later if needed)
    object Notifications : Screen("notifications")
    object HelpSupport : Screen("help_support")
    object About : Screen("about")

    object MatchingStatus : Screen("matching_status")
    object MatchDetail : Screen("match_detail")
    object ConfirmBooking : Screen("confirm_booking")
    object BookingSuccess : Screen("booking_success")
}
// ✅ Routes used inside the nested navigation graph
object Routes {
    const val EVENT_STEP1 = "event_step1"
    const val EVENT_STEP2 = "event_step2"
    const val EVENT_STEP3 = "event_step3"
    const val EVENT_STEP4 = "event_step4"
    const val EVENT_STEP5 = "event_step5"
    const val EVENT_CONFIRM = "event_confirm"
}