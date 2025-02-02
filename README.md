# transferMoneyTestinium
TestiniumWeb - Test Otomasyon Projesi
Bu proje, GAUGE tabanlı test otomasyonu için geliştirilmiş bir Java projesidir. Singleton tasarım deseni, BDD framework yapısı, Maven, Surefire raporlama, ve Log4j2 gibi araçları kullanarak test otomasyonu yapılmaktadır. Proje, Selenium 4 ile web uygulamaları üzerinde testler yapmaktadır.
İçindekiler
Proje Yapısı
Başlangıç
Kullanılan Teknolojiler
Test Komutları
Proje Yapılandırması
Raporlar
Proje Yapısı
Proje, Maven tabanlıdır ve aşağıdaki bağımlılıkları içerir:
GAUGE-Java: Testlerin yazılması için kullanılan BDD framework.
Selenium 4: Web otomasyon testlerini gerçekleştiren araç.
Log4j2: Uygulama loglarının yönetimi için kullanılır.
JUnit 5: Test framework'ü olarak kullanılır.
Maven Surefire Plugin: Testlerin otomatik çalıştırılması ve raporlanması için kullanılır.
Başlangıç
Proje ile başlamak için aşağıdaki adımları takip edebilirsiniz:
1. Gerekli Araçları Kurun
Java 11 veya üzeri
Maven
GAUGE (Maven üzerinden çalıştırılacak)
2. Bağımlılıkları Yükleyin
Maven kullanarak proje bağımlılıklarını yüklemek için terminal üzerinden aşağıdaki komutu çalıştırın:

mvn clean install

Bu komut, pom.xml dosyasındaki bağımlılıkları indirir ve projeyi derler.
3. Testleri Çalıştırın
Testleri çalıştırmak için şu komutu kullanabilirsiniz:

mvn gauge:execute

Bu komut, GAUGE testlerini çalıştıracak ve ilgili raporları oluşturacaktır.
4. Surefire Test Raporlarını Görüntüleyin
Testlerin çıktısı, Surefire plugin'i tarafından yönetilen test raporlarında yer alır. Raporları şu komutla görüntüleyebilirsiniz:

mvn surefire-report:report

Bu komut, target/surefire-reports klasöründe bulunan raporları gösterir.
Kullanılan Teknolojiler
GAUGE: BDD test framework'ü, test senaryolarının yazılmasını sağlar.
Selenium 4: Web uygulamaları üzerinde otomasyon testleri yapar.
JUnit 5: Testlerin çalıştırılması için kullanılan framework.
Log4j2: Testlerin ve uygulama işlemlerinin loglanması için kullanılır.
Maven: Proje yönetimi ve bağımlılık yönetimi için kullanılır.
Surefire Plugin: Maven ile testlerin çalıştırılmasını ve raporlanmasını sağlar.
Test Komutları
Aşağıdaki komutlar, testlerinizi çalıştırmak ve raporları almak için kullanılabilir:

Testleri Çalıştırma

mvn gauge:execute



Surefire Test Raporları

mvn surefire-report:report

Maven ile Bağımlılıkları Yükleme

mvn clean install

Proje Yapılandırması
Projenin pom.xml dosyasında yapılandırmalar aşağıdaki gibi yapılmıştır:
Selenium 4 kullanılarak web otomasyonu sağlanmaktadır.
GAUGE Maven Plugin testlerin çalıştırılması için entegre edilmiştir.
Surefire Plugin kullanılarak test sonuçları raporlanmaktadır.
Log4j2 ile loglama yapılmaktadır.
Raporlar
Testler GAUGE ve Surefire pluginleri kullanılarak raporlanmaktadır.
GAUGE raporları: Testlerin başarısı veya başarısızlığı, her bir adım ve özet bilgiler ile birlikte gösterilir.
Surefire raporları: Testlerin detaylı bir şekilde raporlandığı ve hata mesajlarının verildiği raporlardır.

 
