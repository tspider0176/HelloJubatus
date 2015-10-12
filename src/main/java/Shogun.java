import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import us.jubat.classifier.ClassifierClient;
import us.jubat.classifier.EstimateResult;
import us.jubat.classifier.LabeledDatum;
import us.jubat.common.Datum;

public class Shogun {
    private final ClassifierClient client;
    private final Random random;

    public Shogun(ClassifierClient client) {
        this.client = client;
        this.random = new Random(0);
    }

    private static Datum makeDatum(String name) {
        return new Datum().addString("name", name);
    }

    private static LabeledDatum makeTrain(String tag, String name) {
        return new LabeledDatum(tag, makeDatum(name));
    }

    private void train() {
        LabeledDatum[] trainData = {
                makeTrain("徳川", "家康"),
                makeTrain("徳川", "秀忠"),
                makeTrain("徳川", "家光"),
                makeTrain("徳川", "家綱"),
                makeTrain("徳川", "綱吉"),
                makeTrain("徳川", "家宣"),
                makeTrain("徳川", "家継"),
                makeTrain("徳川", "吉宗"),
                makeTrain("徳川", "家重"),
                makeTrain("徳川", "家治"),
                makeTrain("徳川", "家斉"),
                makeTrain("徳川", "家慶"),
                makeTrain("徳川", "家定"),
                makeTrain("徳川", "家茂"),
                // makeTrain("徳川", "慶喜"),

                makeTrain("足利", "尊氏"),
                makeTrain("足利", "義詮"),
                makeTrain("足利", "義満"),
                makeTrain("足利", "義持"),
                makeTrain("足利", "義量"),
                makeTrain("足利", "義教"),
                makeTrain("足利", "義勝"),
                makeTrain("足利", "義政"),
                makeTrain("足利", "義尚"),
                makeTrain("足利", "義稙"),
                makeTrain("足利", "義澄"),
                makeTrain("足利", "義稙"),
                makeTrain("足利", "義晴"),
                makeTrain("足利", "義輝"),
                makeTrain("足利", "義栄"),
                // makeTrain("足利", "義昭"),

                makeTrain("北条", "時政"), makeTrain("北条", "義時"),
                makeTrain("北条", "泰時"), makeTrain("北条", "経時"),
                makeTrain("北条", "時頼"), makeTrain("北条", "長時"),
                makeTrain("北条", "政村"), makeTrain("北条", "時宗"),
                makeTrain("北条", "貞時"), makeTrain("北条", "師時"),
                makeTrain("北条", "宗宣"), makeTrain("北条", "煕時"),
                makeTrain("北条", "基時"), makeTrain("北条", "高時"),
                makeTrain("北条", "貞顕"),
                // makeTrain("北条", "守時"),
        };
        // prepare training data
        // predict the last ones (that are commented out)
        List<LabeledDatum> t = new ArrayList<>(
                Arrays.asList(trainData));
        Collections.shuffle(t, random);

        // run train
        client.train(t);
    }

    private static EstimateResult findBestResult(List<EstimateResult> res) {
        EstimateResult best = null;
        for (EstimateResult r : res) {
            if (best == null || best.score < r.score) {
                best = r;
            }
        }
        return best;
    }

    private void predict() {
        // predict the last shogun
        Datum[] data = { makeDatum("慶慶"), makeDatum("義昭"), makeDatum("守時") };
        for (Datum datum : data) {
            List<List<EstimateResult>> res = client.classify(
                    Collections.singletonList(datum));
            // get the predicted shogun name
            System.out.println(findBestResult(res.get(0)).label
                    + datum.stringValues.get(0).value);
        }
    }

    public static void main(String[] args) {
        try {
            ClassifierClient client = new ClassifierClient("127.0.0.1", 9199, "test", 1);
            Shogun s = new Shogun(client);
            s.train();
            s.predict();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}