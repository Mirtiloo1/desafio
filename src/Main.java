import br.edu.fatecpg.consumocep.model.Post;
import br.edu.fatecpg.consumocep.service.ConsumoApi;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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
                .filter(p -> p.getTitle().toLowerCase().contains("qui"))
                .sorted(Comparator.comparingInt(Post::getId))
                .toList();

        System.out.println("Posts filtrados e ordenados:");
        procuraQui.forEach(System.out::println);
        System.out.println("\n");

        Map<Integer, Long> agrupadoPorUserId = list.stream()
                .collect(Collectors.groupingBy(Post::getUserId, Collectors.counting()));

        System.out.println("Contagem de posts por userId:");
        agrupadoPorUserId.forEach((userId, count) -> System.out.println("User " + userId + " -> " + count + " posts"));
        System.out.println("\n");

        List<String> titulos = procuraQui.stream()
                .map(Post::getTitle)
                .toList();

        System.out.println("Títulos dos posts filtrados:");
        titulos.forEach(System.out::println);
        System.out.println("\n");

        int somaIds = procuraQui.stream()
                .mapToInt(Post::getId)
                .sum();

        System.out.println("Soma total dos IDs dos posts filtrados: " + somaIds);
    }
}
