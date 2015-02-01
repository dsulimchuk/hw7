package json; /**
 * Created by yfain11 on 4/12/14.
 */
import javax.json.Json;
import javax.json.stream.JsonGenerator;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class JavaToJSONStreaming {

    public static void main(String[] args) {

        Product prd1 = new Product(777, "Gucci Handbag", 1000.00);

        try (OutputStream fos = new FileOutputStream("product_from_stream.json");
            JsonGenerator jsonGenerator = Json.createGenerator(fos)) {

            jsonGenerator.writeStartObject(); // {
            jsonGenerator.write("id", prd1.id);
            jsonGenerator.write("description", prd1.description);
            jsonGenerator.write("price", prd1.price);

            // If you have nested JSON objects,  enclose each of them
            // into a pair of writeStartObject() and writeEnd()

            jsonGenerator.writeEnd();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
