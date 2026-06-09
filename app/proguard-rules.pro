# Keep AgroConsult classes
-keep class com.agroconsult.app.** { *; }
-keepclassmembers class com.agroconsult.app.** { *; }

# Keep Firebase
-keep class com.google.firebase.** { *; }
-keepclassmembers class com.google.firebase.** { *; }
-dontwarn com.google.firebase.**

# Keep Google Play Services
-keep class com.google.android.gms.** { *; }
-keepclassmembers class com.google.android.gms.** { *; }
-dontwarn com.google.android.gms.**

# Keep Stripe
-keep class com.stripe.** { *; }
-keepclassmembers class com.stripe.** { *; }
-dontwarn com.stripe.**

# Keep Agora
-keep class io.agora.** { *; }
-keepclassmembers class io.agora.** { *; }
-dontwarn io.agora.**

# Keep Gson
-keepattributes Signature
-keepattributes *Annotation*
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }

# Keep Kotlin
-keep class kotlin.** { *; }
-keep class kotlinx.** { *; }
-dontwarn kotlin.**

# Keep Compose
-keep class androidx.compose.** { *; }
-keepclassmembers class androidx.compose.** { *; }

# Keep Hilt
-keep class dagger.hilt.** { *; }
-keepclassmembers class dagger.hilt.** { *; }

# Keep model classes
-keep class com.agroconsult.app.data.models.** { *; }
