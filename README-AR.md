# CloudStream Arabic Anime

تم التركيز على الأنمي فقط. هذا المشروع يحتوي على مزوّد واحد يضم خمسة مصادر: Zoro وAnimeX وAnimeFire وKaido وAnimaToon، مع إعداد Gradle وفق نمط مستودع CloudStream الرسمي وسير عمل GitHub Actions لبناء ملف `.cs3`.

## البناء
1. أنشئ مستودع GitHub جديدًا وارفع محتويات هذا المجلد إلى الفرع `main`.
2. افتح تبويب **Actions** وشغّل `Build CloudStream anime plugin`.
3. نزّل artifact باسم `cloudstream-arabic-anime-cs3` بعد اكتمال المهمة.
4. لتقديم مستودع قابل للإضافة داخل التطبيق، يلزم نشر `.cs3` و`plugins.json` على فرع `builds` وتعديل `repo.json` ليشير إلى رابط `plugins.json` الحقيقي.

## ملاحظات
- هذه أول عملية نقل إلى Kotlin وليست ضمانًا لعمل المواقع؛ أسماء النطاقات والمحددات مأخوذة من المصدر الأصلي/أنماط عامة وقد تحتاج تحديثًا.
- لا يتضمن الأرشيف ملف `.cs3` جاهزًا، لأن بناء Gradle يتطلب تنزيل الاعتمادات من الإنترنت وتشغيل GitHub Actions أو بيئة بناء متصلة.
- استندت إعدادات المشروع إلى قالب CloudStream الحالي: https://github.com/recloudstream/TestPlugins
