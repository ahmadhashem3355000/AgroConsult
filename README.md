# أجرو كونسلت - تطبيق الاستشارات الزراعية

## نبذة عن التطبيق

تطبيق موبايل احترافي متخصص في المبيعات الزراعية والاستشارات للمزارعين والمتخصصين في المجال الزراعي.

## الميزات الرئيسية

### 🏪 السوق الزراعي
- عرض وشراء المنتجات الزراعية
- فئات متنوعة (خضروات، فواكه، بذور، أسمدة، مبيدات، معدات)
- نظام السلة والدفع الآمن
- تتبع الطلبيات

### 👨‍🌾 الاستشارات المتخصصة
- استشارات نصية من خبراء زراعيين
- استدعاءات صوتية وفيديوية
- حجز جلسات استشارة مباشرة
- سجل الاستشارات السابقة

### 🗺️ الخريطة التفاعلية
- إيجاد المزارعين والخبراء القريبين
- عرض الموقع الجغرافي
- المسافة والاتجاهات

### 💬 نظام المراسلة
- دردشة مباشرة مع البائعين والخبراء
- إشعارات فورية للرسائل الجديدة
- سجل المحادثات

### ⭐ التقييمات والمراجعات
- تقييم المنتجات والخدمات
- كتابة المراجعات التفصيلية
- عرض تقييمات المستخدمين الآخرين

### 🔔 نظام الإشعارات
- إشعارات للطلبيات الجديدة
- تنبيهات الرسائل
- تحديثات الاستشارات
- عروض وتخفيفات خاصة

### 🌍 دعم اللغة
- دعم كامل للغة العربية
- واجهة سهلة الاستخدام
- ��عم الكتابة من اليمين إلى اليسار

## التكنولوجيا المستخدمة

### Frontend
- **Kotlin** - لغة البرمجة
- **Jetpack Compose** - تصميم الواجهات
- **Android Architecture** - MVVM Pattern
- **Hilt** - Dependency Injection

### Backend & Database
- **Firebase Authentication** - المصادقة
- **Firebase Firestore** - قاعدة البيانات
- **Firebase Storage** - تخزين الصور
- **Firebase Cloud Messaging** - الإشعارات

### APIs & Services
- **Google Maps API** - الخرائط
- **Stripe API** - معالجة الدفع
- **Agora SDK** - مكالمات الفيديو
- **Retrofit** - استدعاءات API

### Libraries
- **Coroutines** - البرمجة غير المتزامنة
- **Room Database** - قاعدة البيانات المحلية
- **Coil** - تحميل الصور
- **Navigation** - التنقل بين الشاشات

## متطلبات التطوير

- Android Studio Flamingo أو أحدث
- JDK 17 أو أحدث
- Gradle 8.0+
- Android SDK 34+
- Minimum API Level: 24 (Android 7.0)

## خطوات التثبيت

```bash
# استنساخ المستودع
git clone https://github.com/ahmadhashem3355000/AgroConsult.git

# الدخول إلى المشروع
cd AgroConsult

# فتح المشروع في Android Studio
open -a "Android Studio" .

# أو بناء التطبيق من سطر الأوامر
./gradlew build

# تشغيل التطبيق
./gradlew installDebug
```

## هيكل المشروع

```
AgroConsult/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── AndroidManifest.xml
│   │   │   ├── java/
│   │   │   │   └── com/agroconsult/
│   │   │   │       ├── data/
│   │   │   │       │   ├── models/
│   │   │   │       │   ├── repositories/
│   │   │   │       │   └── remote/
│   │   │   │       ├── ui/
│   │   │   │       │   ├── screens/
│   │   │   │       │   ├── navigation/
│   │   │   │       │   └── components/
│   │   │   │       ├── viewmodel/
│   │   │   │       ├── utils/
│   │   │   │       └── MainActivity.kt
│   │   │   └── res/
│   │   │       ├── values/
│   │   │       ├── drawable/
│   │   │       ├── mipmap/
│   │   │       └── layout/
│   │   └── test/
│   └── build.gradle
├── build.gradle
├── settings.gradle
└── README.md
```

## المساهمة

نرحب بالمساهمات! يرجى اتباع خطوات المساهمة التالية:

1. Fork المستودع
2. إنشاء فرع جديد (`git checkout -b feature/AmazingFeature`)
3. Commit التغييرات (`git commit -m 'Add some AmazingFeature'`)
4. Push إلى الفرع (`git push origin feature/AmazingFeature`)
5. فتح Pull Request

## الترخيص

هذا المشروع مرخص تحت MIT License - انظر ملف LICENSE للتفاصيل.

## الدعم والتواصل

للأسئلة والدعم، يرجى فتح issue جديد في المستودع.

---

**تم التطوير بواسطة:** Ahmad Hashem
**آخر تحديث:** 2025
