package pokebattler.service;

import pokebattler.exception.PokemonNotFoundException;
import pokebattler.model.Pokemon;
import pokebattler.model.Type;

import java.util.ArrayList;
import java.util.List;

public class Manager {

    private static List<Pokemon> pokedexList = new ArrayList<>();

    public static List<Pokemon> getPokedexList() {
        return pokedexList;
    }

    public static void setPokedexList(List<Pokemon> list) {
        pokedexList = list;
    }

    public static List<Pokemon> filterByType(Type type, List<Pokemon> list) {
        List<Pokemon> result = new ArrayList<>();
        for (Pokemon p : list) {
            if (p.getType() == type) {
                result.add(p);
            }
        }
        return result;
    }

    public static List<Pokemon> searchByName(String query) {
        List<Pokemon> result = new ArrayList<>();
        String lowerQuery = query.toLowerCase().trim();
        for (Pokemon p : pokedexList) {
            if (p.getName().toLowerCase().contains(lowerQuery)) {
                result.add(p);
            }
        }
        return result;
    }

    public static Pokemon findByName(String name) {
        for (Pokemon p : pokedexList) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        throw new PokemonNotFoundException("Ingen Pokémon med namnet '" + name + "' hittades.");
    }

    public static void addPokemon(Pokemon p) {
        pokedexList.add(p);
    }

    public static void removePokemon(Pokemon p) {
        pokedexList.remove(p);
    }

    // Bubble sort-algoritm, modifierad från https://codegym.cc/groups/posts/bubble-sort
    // Viktigaste ändringen är att compareToIgnoreCase används för rangordna bokstäver alfabetiskt
    public static List<Pokemon> sortByNameBubble(List<Pokemon> list) {
        List<Pokemon> sortedList = new ArrayList<>(list);

        for (int i = 0; i < sortedList.size(); i++) {
            for (int j = 1; j < (sortedList.size() - i); j++) {

                if (sortedList.get(j - 1).getName().compareToIgnoreCase(sortedList.get(j).getName()) > 0) {
                    Pokemon temp = sortedList.get(j - 1);
                    sortedList.set(j - 1, sortedList.get(j));
                    sortedList.set(j, temp);
                }
            }
        }
        return sortedList;
    }
}

