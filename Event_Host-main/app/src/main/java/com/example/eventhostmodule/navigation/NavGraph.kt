package com.example.eventhostmodule.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.example.eventhostmodule.data.model.EventViewModel
import com.example.eventhostmodule.ui.screens.auth.LoginScreen
import com.example.eventhostmodule.ui.screens.auth.OtpVerificationScreen
import com.example.eventhostmodule.ui.screens.auth.RoleSelectionScreen
import com.example.eventhostmodule.ui.screens.host.*
import com.example.eventhostmodule.ui.screens.onboarding.*
import com.example.eventhostmodule.ui.splash.SplashScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavGraph(
    navController: NavHostController,
    eventViewModel: EventViewModel          // ✅ received from MainActivity
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route   // ✅ no route="root"
    ) {

        composable(
            route = Screen.Splash.route,
            exitTransition = {
                fadeOut(animationSpec = tween(500)) +
                        slideOutHorizontally(targetOffsetX = { -it / 2 }, animationSpec = tween(500))
            }
        ) { SplashScreen(navController) }

        composable(
            route = Screen.Onboarding1.route,
            enterTransition = {
                fadeIn(animationSpec = tween(600)) +
                        slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(600))
            }
        ) {
            OnboardingScreen1(
                onNext = { navController.navigate(Screen.Onboarding2.route) },
                onSkip = { navController.navigate(Screen.Login.route) }
            )
        }

        composable(
            route = Screen.Onboarding2.route,
            enterTransition = { fadeIn(animationSpec = tween(500)) + slideInHorizontally { it } }
        ) {
            OnboardingScreen2(
                onNext = { navController.navigate(Screen.Onboarding3.route) },
                onSkip = { navController.navigate(Screen.Login.route) }
            )
        }

        composable(
            route = Screen.Onboarding3.route,
            enterTransition = { fadeIn(animationSpec = tween(500)) + slideInHorizontally { it } }
        ) {
            OnboardingScreen3(
                onNext = { navController.navigate(Screen.Login.route) },
                onSkip = { navController.navigate(Screen.Login.route) }
            )
        }

        composable(
            route = Screen.Login.route,
            enterTransition = { fadeIn(animationSpec = tween(500)) + slideInHorizontally { it } }
        ) {
            LoginScreen(onLogin = { navController.navigate(Screen.OtpVerification.route) })
        }

        composable(Screen.OtpVerification.route) {
            OtpVerificationScreen(
                onBack = { navController.popBackStack() },
                onVerify = { navController.navigate(Screen.RoleSelection.route) }
            )
        }

        composable(Screen.RoleSelection.route) {
            RoleSelectionScreen(
                onContinue = { navController.navigate(Screen.HostHome.route) },
                onLogin = { navController.navigate(Screen.Login.route) }
            )
        }

        composable(
            route = Screen.HostHome.route + "?progress={progress}",
            arguments = listOf(navArgument("progress") { defaultValue = 0.5f })
        ) { backStackEntry ->
            val progress = backStackEntry.arguments?.getFloat("progress") ?: 0.5f
            EventHostHomeScreen(
                navController = navController,
                viewModel = eventViewModel,     // ✅ pass shared viewModel directly
                profileProgress = progress
            )
        }

        composable(Screen.CompleteProfileIntro.route) {
            CompleteProfileIntroScreen(
                onStartClick = { navController.navigate(Screen.CompleteProfile.route) },
                onClose = { navController.popBackStack() }
            )
        }

        composable(Screen.CompleteProfile.route) { CompleteProfileScreen(navController) }
        composable(Screen.CompleteProfilePhoto.route) { CompleteProfilePhotoScreen(navController) }
        composable(Screen.ProfileSuccess.route) { ProfileSuccessScreen(navController) }
        composable(Screen.KycAadhaar.route) { KycAadhaarScreen(navController) }
        composable(Screen.KycPan.route) { KycPanScreen(navController) }
        composable(Screen.KycPayment.route) { KycPaymentScreen(navController) }
        composable(Screen.Chat.route) { ChatScreen(navController) }
        composable(Screen.Wallet.route) { WalletScreen(navController) }
        composable(Screen.Profile.route) { ProfileScreen(navController) }
        composable(Screen.Notifications.route) { NotificationsScreen(navController) }
        composable(Screen.HelpSupport.route) { HelpSupportScreen(navController) }
        composable(Screen.About.route) { AboutScreen(navController) }
        composable(Screen.MatchingStatus.route) { MatchingStatusScreen(navController) }
        composable(Screen.MatchDetail.route) { MatchDetailScreen(navController) }

        composable(Screen.ConfirmBooking.route) {
            ConfirmBookingScreen(navController, eventViewModel)  // ✅ shared
        }

        composable(Screen.BookingSuccess.route) {
            BookingSuccessScreen(navController, eventViewModel)  // ✅ shared
        }

        // ✅ EVENT FLOW
        navigation(
            startDestination = Routes.EVENT_STEP1,
            route = Screen.EventFlow.route
        ) {
            composable(Routes.EVENT_STEP1) {
                EventStep1Screen(navController, eventViewModel)
            }
            composable(Routes.EVENT_STEP2) {
                EventStep2Screen(navController, eventViewModel)
            }
            composable(Routes.EVENT_STEP3) {
                EventStep3Screen(navController, eventViewModel)
            }
            composable(Routes.EVENT_STEP4) {
                EventStep4Screen(navController, eventViewModel)
            }
            composable(Routes.EVENT_STEP5) {
                EventStep5Screen(navController, eventViewModel)
            }
            composable(Routes.EVENT_CONFIRM) {
                EventConfirmScreen(navController, eventViewModel)
            }
        }
    }
}