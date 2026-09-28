package pokebattler.storage;

import pokebattler.model.Attack;
import pokebattler.model.Pokemon;
import pokebattler.model.Type;

import java.util.ArrayList;
import java.util.List;

public class SeedData {
    public static List<Pokemon> loadSeedData() {
        List<Attack> charmanderAttacks = List.of(
                new Attack("Scratch", Type.NORMAL, 10, 1.0),
                new Attack("Growl", Type.NORMAL, 1, 1.0),
                new Attack("Ember", Type.FIRE, 15, .95),
                new Attack("Smokescreen", Type.NORMAL, 1, 1.0)
        );
        Pokemon charmander = new Pokemon("Charmander", Type.FIRE, 39, 39, charmanderAttacks);

        List<Attack> squirtleAttacks = List.of(
                new Attack("Tackle", Type.NORMAL, 10, 1.0)
        );
        Pokemon squirtle = new Pokemon("Squirtle", Type.WATER, 44, 44, squirtleAttacks);

        List<Attack> bulbasaurAttacks = List.of(
                new Attack("Tackle", Type.NORMAL, 10, 1.0),
                new Attack("Growl", Type.NORMAL, 1, 1.0),
                new Attack("Vine whip", Type.GRASS, 13, 1.0),
                new Attack("Leech seed", Type.GRASS, 1, .9)
        );
        Pokemon bulbasaur = new Pokemon("Bulbasaur", Type.GRASS, 45, 45, bulbasaurAttacks);

        List<Attack> pikachuAttacks = List.of(
                new Attack("Quick attack", Type.NORMAL, 8, 1.0),
                new Attack("Tail whip", Type.NORMAL, 1, 1.0),
                new Attack("Thunder shock", Type.ELECTRIC, 16, 1.0),
                new Attack("Thunder wave", Type.ELECTRIC, 1, .9)
        );
        Pokemon pikachu = new Pokemon("Pikachu", Type.ELECTRIC, 35, 35, pikachuAttacks);

        List<Attack> rattataAttacks = List.of(
                new Attack("Tackle", Type.NORMAL, 8, 1.0),
                new Attack("Tail whip", Type.NORMAL, 1, 1.0),
                new Attack("Quick attack", Type.NORMAL, 10, 1.0),
                new Attack("Hyper fang", Type.NORMAL, 18, .9)
        );
        Pokemon rattata = new Pokemon("Rattata", Type.NORMAL, 30, 30, rattataAttacks);

        List<Attack> vulpixAttacks = List.of(
                new Attack("Scratch", Type.NORMAL, 9, 1.0),
                new Attack("Tail whip", Type.NORMAL, 1, 1.0),
                new Attack("Ember", Type.FIRE, 14, .95),
                new Attack("Roar", Type.NORMAL, 1, 1.0)
        );
        Pokemon vulpix = new Pokemon("Vulpix", Type.FIRE, 38, 38, vulpixAttacks);

        List<Attack> psyduckAttacks = List.of(
                new Attack("Scratch", Type.NORMAL, 9, 1.0),
                new Attack("Tail whip", Type.NORMAL, 1, 1.0),
                new Attack("Water gun", Type.WATER, 13, 1.0),
                new Attack("Confusion", Type.NORMAL, 12, 1.0)
        );
        Pokemon psyduck = new Pokemon("Psyduck", Type.WATER, 50, 50, psyduckAttacks);

        List<Attack> oddishAttacks = List.of(
                new Attack("Tackle", Type.NORMAL, 9, 1.0),
                new Attack("Absorb", Type.GRASS, 6, 1.0),
                new Attack("Vine whip", Type.GRASS, 12, 1.0),
                new Attack("Poison powder", Type.GRASS, 1, .75)
        );
        Pokemon oddish = new Pokemon("Oddish", Type.GRASS, 45, 45, oddishAttacks);

        List<Attack> magnemiteAttacks = List.of(
                new Attack("Tackle", Type.NORMAL, 8, 1.0),
                new Attack("Thunder shock", Type.ELECTRIC, 15, 1.0),
                new Attack("Sonic boom", Type.NORMAL, 20, .9),
                new Attack("Supersonic", Type.NORMAL, 1, .55)
        );
        Pokemon magnemite = new Pokemon("Magnemite", Type.ELECTRIC, 25, 25, magnemiteAttacks);

        List<Attack> eeveeAttacks = List.of(
                new Attack("Tackle", Type.NORMAL, 10, 1.0),
                new Attack("Tail whip", Type.NORMAL, 1, 1.0),
                new Attack("Quick attack", Type.NORMAL, 12, 1.0),
                new Attack("Sand attack", Type.NORMAL, 1, 1.0)
        );
        Pokemon eevee = new Pokemon("Eevee", Type.NORMAL, 55, 55, eeveeAttacks);

        List<Attack> pidgeyAttacks = List.of(
                new Attack("Tackle", Type.NORMAL, 8, 1.0),
                new Attack("Gust", Type.NORMAL, 10, 1.0),
                new Attack("Quick attack", Type.NORMAL, 11, 1.0),
                new Attack("Sand attack", Type.NORMAL, 1, 1.0)
        );
        Pokemon pidgey = new Pokemon("Pidgey", Type.NORMAL, 40, 40, pidgeyAttacks);

        List<Attack> caterpieAttacks = List.of(
                new Attack("Tackle", Type.NORMAL, 8, 1.0),
                new Attack("String shot", Type.GRASS, 1, .9),
                new Attack("Bug bite", Type.GRASS, 10, 1.0)
        );
        Pokemon caterpie = new Pokemon("Caterpie", Type.GRASS, 45, 45, caterpieAttacks);

        List<Attack> weedleAttacks = List.of(
                new Attack("Tackle", Type.NORMAL, 8, 1.0),
                new Attack("String shot", Type.GRASS, 1, .9),
                new Attack("Poison sting", Type.GRASS, 9, 1.0)
        );
        Pokemon weedle = new Pokemon("Weedle", Type.GRASS, 40, 40, weedleAttacks);

        List<Attack> zubatAttacks = List.of(
                new Attack("Tackle", Type.NORMAL, 8, 1.0),
                new Attack("Supersonic", Type.NORMAL, 1, .55),
                new Attack("Bite", Type.NORMAL, 12, 1.0),
                new Attack("Absorb", Type.GRASS, 7, 1.0)
        );
        Pokemon zubat = new Pokemon("Zubat", Type.GRASS, 40, 40, zubatAttacks);

        List<Attack> geodudeAttacks = List.of(
                new Attack("Tackle", Type.NORMAL, 10, 1.0),
                new Attack("Rock throw", Type.NORMAL, 14, .9),
                new Attack("Defense curl", Type.NORMAL, 1, 1.0)
        );
        Pokemon geodude = new Pokemon("Geodude", Type.NORMAL, 40, 40, geodudeAttacks);

        List<Attack> ponytaAttacks = List.of(
                new Attack("Tackle", Type.NORMAL, 10, 1.0),
                new Attack("Ember", Type.FIRE, 14, .95),
                new Attack("Quick attack", Type.NORMAL, 11, 1.0),
                new Attack("Tail whip", Type.NORMAL, 1, 1.0)
        );
        Pokemon ponyta = new Pokemon("Ponyta", Type.FIRE, 50, 50, ponytaAttacks);

        List<Attack> voltorbAttacks = List.of(
                new Attack("Tackle", Type.NORMAL, 8, 1.0),
                new Attack("Thunder shock", Type.ELECTRIC, 15, 1.0),
                new Attack("Sonic boom", Type.NORMAL, 20, .9),
                new Attack("Supersonic", Type.NORMAL, 1, .55)
        );
        Pokemon voltorb = new Pokemon("Voltorb", Type.ELECTRIC, 40, 40, voltorbAttacks);

        List<Attack> meowthAttacks = List.of(
                new Attack("Scratch", Type.NORMAL, 10, 1.0),
                new Attack("Growl", Type.NORMAL, 1, 1.0),
                new Attack("Bite", Type.NORMAL, 13, 1.0),
                new Attack("Quick attack", Type.NORMAL, 11, 1.0)
        );
        Pokemon meowth = new Pokemon("Meowth", Type.NORMAL, 40, 40, meowthAttacks);

        List<Attack> poliwagAttacks = List.of(
                new Attack("Scratch", Type.NORMAL, 9, 1.0),
                new Attack("Water gun", Type.WATER, 13, 1.0),
                new Attack("Confusion", Type.NORMAL, 12, 1.0)
        );
        Pokemon poliwag = new Pokemon("Poliwag", Type.WATER, 40, 40, poliwagAttacks);

        List<Attack> bellsproutAttacks = List.of(
                new Attack("Vine whip", Type.GRASS, 12, 1.0),
                new Attack("Growth", Type.GRASS, 1, 1.0),
                new Attack("Tackle", Type.NORMAL, 9, 1.0),
                new Attack("Poison powder", Type.GRASS, 1, .75)
        );
        Pokemon bellsprout = new Pokemon("Bellsprout", Type.GRASS, 50, 50, bellsproutAttacks);

        List<Attack> horseaAttacks = List.of(
                new Attack("Tackle", Type.NORMAL, 8, 1.0),
                new Attack("Water gun", Type.WATER, 13, 1.0),
                new Attack("Smokescreen", Type.NORMAL, 1, 1.0)
        );
        Pokemon horsea = new Pokemon("Horsea", Type.WATER, 30, 30, horseaAttacks);

        List<Attack> growlitheAttacks = List.of(
                new Attack("Bite", Type.NORMAL, 12, 1.0),
                new Attack("Ember", Type.FIRE, 14, .95),
                new Attack("Roar", Type.NORMAL, 1, 1.0),
                new Attack("Quick attack", Type.NORMAL, 11, 1.0)
        );
        Pokemon growlithe = new Pokemon("Growlithe", Type.FIRE, 55, 55, growlitheAttacks);

        List<Attack> seelAttacks = List.of(
                new Attack("Tackle", Type.NORMAL, 9, 1.0),
                new Attack("Water gun", Type.WATER, 13, 1.0),
                new Attack("Hypnosis", Type.NORMAL, 1, .6),
                new Attack("Bubble", Type.WATER, 10, 1.0)
        );
        Pokemon seel = new Pokemon("Seel", Type.WATER, 65, 65, seelAttacks);

        List<Attack> exeggcuteAttacks = List.of(
                new Attack("Tackle", Type.NORMAL, 8, 1.0),
                new Attack("Absorb", Type.GRASS, 7, 1.0),
                new Attack("Leech seed", Type.GRASS, 1, .9),
                new Attack("Confusion", Type.NORMAL, 12, 1.0)
        );
        Pokemon exeggcute = new Pokemon("Exeggcute", Type.GRASS, 60, 60, exeggcuteAttacks);

        List<Attack> dratiniAttacks = List.of(
                new Attack("Wrap", Type.NORMAL, 10, .9),
                new Attack("Leer", Type.NORMAL, 1, 1.0),
                new Attack("Thunder wave", Type.ELECTRIC, 1, .9),
                new Attack("Twister", Type.NORMAL, 12, 1.0)
        );
        Pokemon dratini = new Pokemon("Dratini", Type.NORMAL, 41, 41, dratiniAttacks);

        return new ArrayList<>(List.of(
                charmander, squirtle, bulbasaur, pikachu, rattata,
                vulpix, psyduck, oddish, magnemite, eevee,
                pidgey, caterpie, weedle, zubat, geodude,
                ponyta, voltorb, meowth, poliwag, bellsprout,
                horsea, growlithe, seel, exeggcute, dratini
        ));
    }
}
