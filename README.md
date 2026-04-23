# Otonom Araç Navigasyon Güvenlik Modülü (SVM)

Bu proje, otonom araç navigasyon sistemlerinde engel sınıflandırmasını optimize etmek amacıyla geliştirilmiş, **Lineer Destek Vektör Makinesi (Support Vector Machine - SVM)** tabanlı bir güvenlik modülüdür. Yazılım, 2 boyutlu bir düzlemdeki engelleri "güvenli" ve "engel" olarak ayıran en geniş güvenlik koridorunu (Maksimum Marjin) hesaplar.

## 📌 Proje Özeti
Otonom sistemlerde herhangi bir ayırıcı çizgi yeterli değildir; sistem güvenliği için nesneler arasındaki hata payının maksimize edilmesi gerekir. Bu proje, **Hinge Loss** ve **Gradient Descent** yöntemlerini kullanarak iki veri kümesi arasındaki en optimum sınırı bulan bir Java uygulamasıdır.

## 🛠 Teknik Özellikler ve Mimari
Proje, Nesne Yönelimli Programlama (OOP) prensiplerine uygun olarak katmanlı bir yapıda geliştirilmiştir:

- **`ObstaclePoint` Sınıfı**: Veri modelini temsil eder. Koordinatları (x, y) ve sınıf etiketini kapsülleyerek (Encapsulation) veri bütünlüğünü sağlar.
- **`LinearSVM` Sınıfı**: Algoritmanın çekirdeğidir. Ağırlık vektörü (w) ve sapma katsayısını (b) iteratif olarak eğitir.
- **Matematiksel Model**: Karar sınırı $w \cdot x + b = 0$ denklemi üzerine kuruludur.
- **Optimizasyon**: Menteşe Kaybı (Hinge Loss) fonksiyonu kullanılarak hata payı minimize edilmiştir.

## 📊 Algoritma Analizi (Big-O)
Yazılımın performansı, gömülü sistemlerin (embedded systems) kısıtlı kaynakları göz önünde bulundurularak optimize edilmiştir:

- **Zaman Karmaşıklığı**: $T = O(I \times N)$
  - $I$: İterasyon sayısı, $N$: Veri seti büyüklüğü. Eğitim süresi veri miktarıyla doğrusal (lineer) olarak artar.
- **Alan Karmaşıklığı**: $S = O(N)$
  - Bellekte sadece $N$ adet nesne tutulur, bu da düşük kaynaklı araç bilgisayarları için uygundur.

## 🧠 Bellek Yönetimi (Sıfır Sızıntı Stratejisi)
Otonom sistemlerin kesintisiz çalışması için bellek güvenliği kritiktir:
- Eğitim süreci bittiğinde `dataset.clear()` komutu çağrılarak referanslar temizlenir ve Java Garbage Collector (GC) üzerindeki baskı azaltılır.
- Döngü içerisinde gereksiz nesne üretiminden kaçınılarak **Heap Memory** verimli kullanılmıştır.

## 🚀 Nasıl Çalıştırılır?
1. Projeyi bilgisayarınıza klonlayın:
   ```bash
   git clone [https://github.com/kullanici-adi/proje-adi.git](https://github.com/kullanici-adi/proje-adi.git)

