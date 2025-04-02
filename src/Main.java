import br.edu.fatecpg.consumocep.model.Post;
import br.edu.fatecpg.consumocep.service.ConsumoApi;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        String resp = ConsumoApi.obterDados("https://jsonplaceholder.typicode.com/posts");

        JsonArray jsonArray = JsonParser.parseString(resp).getAsJsonArray();
        List<Post> list = new ArrayList<>();
        for (JsonElement element : jsonArray) {
            Post obj = new Gson().fromJson(element, Post.class);
            list.add(obj);
        }

        List<Post> procuraQui = list.stream()
                .filter(p -> p.getTitle().contains("qui"))
                .toList();
        procuraQui.forEach(System.out::println);

        List<Integer> ordenaId = list.stream()
                .map(Post::getId)
                .sorted()
                .toList();

        List<Post> ordenarPosts = list.stream()
                .filter(p -> p.getTitle().contentEquals(ordenaId.toString()))
                .toList();
        ordenarPosts.forEach(System.out::println);

        List<Post> agrupar = (List<Post>) list.stream()
                .collect(Collectors.groupingBy(Post::getUserId, Collectors.counting()));

        System.out.println(agrupar);


    }
}