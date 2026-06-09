# AgroConsult - دليل البدء السريع

## المتطلبات الأساسية

- Android Studio Flamingo أو أحدث
- JDK 17 أو أحدث
- Gradle 8.0+
- Android SDK 34+
- حساب Google Cloud Platform
- حساب Firebase

## التثبيت والإعداد

### 1. استنساخ المستودع
```bash
git clone https://github.com/ahmadhashem3355000/AgroConsult.git
cd AgroConsult
```

### 2. فتح المشروع في Android Studio
```bash
open -a "Android Studio" .
```

### 3. إعداد Firebase

#### أ) إنشاء مشروع Firebase
1. اذهب إلى [Firebase Console](https://console.firebase.google.com/)
2. انقر على "Create Project"
3. أدخل اسم المشروع: `AgroConsult`
4. اختر منطقتك
5. انقر على "Create"

#### ب) إضافة تطبيق Android
1. في Firebase Console، انقر على "Add app" ثم اختر Android
2. أدخل اسم الحزمة: `com.agroconsult.app`
3. أدخل اسم التطبيق: `AgroConsult`
4. احصل على شهادة التوقيع SHA-1:
```bash
keytool -list -v -keystore ~/.android/debug.keystore -alias androiddebugkey -storepass android -keypass android
```
5. حمل ملف `google-services.json`
6. ضعه في مجلد `app/`

### 4. إعداد Google Maps API

1. اذهب إلى [Google Cloud Console](https://console.cloud.google.com/)
2. اختر مشروعك
3. فعّل Google Maps Android API
4. أنشئ API Key
5. أضفها في `AndroidManifest.xml`:
```xml
<meta-data
    android:name="com.google.android.geo.API_KEY"
    android:value="YOUR_API_KEY" />
```

### 5. إعداد Stripe (اختياري)

1. اذهب إلى [Stripe Dashboard](https://dashboard.stripe.com/)
2. احصل على Publishable Key
3. أضفها في التطبيق عند الحاجة

### 6. تشغيل التطبيق

```bash
# بناء التطبيق
./gradlew build

# تشغيل على جهاز محاكي
./gradlew installDebug
```

أو استخدم Android Studio:
1. اختر جهاز محاكي أو جهاز فعلي
2. انقر على Run ▶️

## الميزات الرئيسية

### 🏠 الشاشة الرئيسية
- عرض المنتجات المميزة
- فئات سريعة
- الخبراء القريبين

### 🛒 السوق الزراعي
- تصفح المنتجات
- البحث والتصفية
- إضافة منتج جديد
- تقييم المنتجات

### 👨‍⚕️ الاستشارات
- عرض الخبراء المتاحين
- حجز استشارات
- استشارات نصية وفيديوية وصوتية

### 💬 الرسائل
- محادثة مباشرة مع المستخدمين
- إرسال الصور
- الرسائل الفورية

### 👤 الملف الشخصي
- إدارة بيانات المستخدم
- عرض الطلبات والمبيعات
- الإحصائيات

## البنية المشروعية

```
AgroConsult/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/agroconsult/app/
│   │   │   │   ├── data/
│   │   │   │   │   ├── models/          # نماذج البيانات
│   │   │   │   │   ├── remote/         # مستودعات Firebase
│   │   │   │   │   └── di/             # Dependency Injection
│   │   │   │   ├── ui/
│   │   │   │   │   ├── screens/        # الشاشات
│   │   │   │   │   ├── viewmodel/      # ViewModels
│   │   │   │   │   ├── components/     # المكونات المشتركة
│   │   │   │   │   ├── navigation/     # التنقل
│   │   │   │   │   └── theme/          # التصميم
│   │   │   │   ├── services/           # الخدمات
│   │   │   │   └── utils/              # الأدوات والثوابت
│   │   │   ├── res/
│   │   │   │   ├── values/
│   │   │   │   ├── drawable/
│   │   │   │   └── mipmap/
│   │   │   └── AndroidManifest.xml
│   │   └── test/
│   ├── build.gradle
│   └── proguard-rules.pro
├── build.gradle
├── settings.gradle
└── README.md
```

## المعايير والأفضليات

### معايير الكود
- اتبع [Kotlin Coding Standards](https://kotlinlang.org/docs/coding-conventions.html)
- استخدم Material Design 3
- اكتب التعليقات للكود المعقد
- استخدم meaningful names للمتغيرات والدوال

### المكتبات المستخدمة
- **Firebase** - المصادقة وقاعدة البيانات
- **Jetpack Compose** - واجهة المستخدم
- **Hilt** - Dependency Injection
- **Coroutines** - البرمجة غير المتزامنة
- **Room** - قاعدة البيانات المحلية
- **Google Maps** - الخرائط والموقع
- **Retrofit** - استدعاءات API

## التطوير والاختبار

### كتابة الاختبارات
```kotlin
@RunWith(AndroidJUnit4::class)
class ExampleTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}
```

### تشغيل الاختبارات
```bash
# اختبارات الوحدة
./gradlew test

# اختبارات Android
./gradlew connectedAndroidTest
```

## نصائح التطوير

1. استخدم Firebase Emulator للتطوير المحلي
2. فعّل ProGuard/R8 في production
3. اختبر على أجهزة بأحجام مختلفة
4. استخدم اللغة العربية في جميع النصوص
5. اتبع Material Design guidelines

## استكشاف الأخطاء

### المشكلة: Firebase connection fails
**الحل:** تأكد من ملف `google-services.json` وصحة الشهادات

### المشكلة: Google Maps not showing
**الحل:** تحقق من API Key والتصاريح في AndroidManifest.xml

### المشكلة: Gradle sync fails
**الحل:** احذف مجلد `.gradle` و `build` ثم أعد المحاولة

## الموارد الإضافية

- [Firebase Documentation](https://firebase.google.com/docs)
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Android Developer Guide](https://developer.android.com/guide)
- [Kotlin Documentation](https://kotlinlang.org/docs)

## الدعم والتواصل

- 📧 البريد الإلكتروني: support@agroconsult.com
- 🐛 الإبلاغ عن الأخطاء: [GitHub Issues](https://github.com/ahmadhashem3355000/AgroConsult/issues)
- 💬 النقاش والأسئلة: [GitHub Discussions](https://github.com/ahmadhashem3355000/AgroConsult/discussions)

## الترخيص

هذا المشروع مرخص تحت [MIT License](LICENSE)

## الشكر والتقدير

شكر خاص لجميع المساهمين والمستخدمين الذين يساعدون في تحسين التطبيق.

---

**آخر تحديث:** 2025-06-09
**الإصدار:** 1.0.0
