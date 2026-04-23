import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 1. Veri Sınıfı: Düzlemdeki koordinatları ve engel sınıfını temsil eder.
 */
class ObstaclePoint {
    private final double x;
    private final double y;
    private final int classification; // 1 veya -1 olmalıdır

    public ObstaclePoint(double x, double y, int classification) {
        this.x = x;
        this.y = y;
        this.classification = classification;
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public int getClassification() { return classification; }
}

/**
 * 2. Algoritma Sınıfı: Lineer Destek Vektör Makinesi (Maksimum Marjin)
 */
class LinearSVM {
    private double[] weights;
    private double bias;
    private final double learningRate;
    private final double lambdaParam; // Marjin maksimizasyonu için düzenlileştirme parametresi
    private final int numIterations;

    public LinearSVM(double learningRate, double lambdaParam, int numIterations) {
        this.learningRate = learningRate;
        this.lambdaParam = lambdaParam;
        this.numIterations = numIterations;
        this.weights = new double[2]; // 2 Boyutlu koordinat düzlemi için
        this.bias = 0.0;
    }

    // Algoritmanın Eğitilmesi (Gradient Descent)
    public void train(List<ObstaclePoint> dataset) {
        for (int i = 0; i < numIterations; i++) {
            for (ObstaclePoint point : dataset) {
                // Hinge Loss kontrolü: y_i * (w * x_i + b) >= 1
                double linearOutput = (weights[0] * point.getX()) + (weights[1] * point.getY()) + bias;
                boolean isCorrectlyClassifiedAndOutsideMargin = (point.getClassification() * linearOutput) >= 1;

                if (isCorrectlyClassifiedAndOutsideMargin) {
                    // Doğru bölgede: Sadece marjini genişletmek için ağırlıkları küçült
                    weights[0] -= learningRate * (2 * lambdaParam * weights[0]);
                    weights[1] -= learningRate * (2 * lambdaParam * weights[1]);
                } else {
                    // Hata var veya marjin içinde: Noktayı doğru yöne itecek güncellemeyi yap
                    weights[0] -= learningRate * (2 * lambdaParam * weights[0] - point.getX() * point.getClassification());
                    weights[1] -= learningRate * (2 * lambdaParam * weights[1] - point.getY() * point.getClassification());
                    bias += learningRate * point.getClassification();
                }
            }
        }
    }

    // Ayrıştırıcı denklemin katsayılarını döndürür
    public String getBoundaryEquation() {
        return String.format("%.4fx + %.4fy + %.4f = 0", weights[0], weights[1], bias);
    }
}

/**
 * 3. Main Sınıfı: Uygulamanın çalıştırılması ve Demo
 */
public class Main {
    public static void main(String[] args) {
        List<ObstaclePoint> dataset = generateDummyData();

        // Sınıf -1 Noktaları
        System.out.println("--- SINIF -1 ---");
        for (ObstaclePoint p : dataset) {
            if (p.getClassification() == -1) {
                System.out.print("(" + p.getX() + "," + p.getY() + "),");
            }
        }

// Sınıf 1 Noktaları
        System.out.println("\n--- SINIF 1 ---");
        for (ObstaclePoint p : dataset) {
            if (p.getClassification() == 1) {
                System.out.print("(" + p.getX() + "," + p.getY() + "),");
            }
        }

        // Algoritmayı başlatıyoruz: Öğrenme oranı, lambda (marjin cezası) ve iterasyon sayısı
        LinearSVM svm = new LinearSVM(0.001, 0.01, 10000);

        System.out.println("Otonom Araç Güvenlik Modülü Başlatılıyor...");
        System.out.println("Koordinat verileri işleniyor, optimum ayrıştırıcı sınır hesaplanıyor...\n");

        long startTime = System.nanoTime();
        svm.train(dataset);
        long endTime = System.nanoTime();

        System.out.println("--- SONUÇ ---");
        System.out.println("En Geniş Güvenlik Koridoru (Ayrıştırıcı Denklem): " + svm.getBoundaryEquation());
        System.out.println("Hesaplama Süresi: " + (endTime - startTime) / 1_000_000.0 + " ms");

        // Bellek yönetimi: Dataset ile işimiz bittiğinde referansı temizleyerek Garbage Collector'a yardımcı olabiliriz.
        dataset.clear();
    }

    // Test için doğrusal olarak ayrılabilir rastgele 2 sınıf veri üretir
    private static List<ObstaclePoint> generateDummyData() {
        List<ObstaclePoint> points = new ArrayList<>();
        Random rand = new Random();

        // Sınıf 1 (Örn: Haritanın sol alt bölgesi)
        for (int i = 0; i < 50; i++) {
            points.add(new ObstaclePoint(rand.nextDouble() * 5, rand.nextDouble() * 5, -1));
        }
        // Sınıf 2 (Örn: Haritanın sağ üst bölgesi, daha yakın)
        for (int i = 0; i < 50; i++) {
            points.add(new ObstaclePoint(rand.nextDouble() * 5 + 7, rand.nextDouble() * 5 + 7, 1));
        }
        return points;
    }
}
