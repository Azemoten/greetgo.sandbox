package kz.greetgo.sandbox.register.dto;

import kz.greetgo.sandbox.controller.model.Charm;
import kz.greetgo.sandbox.controller.model.Client;
import kz.greetgo.sandbox.controller.model.Filter;
import kz.greetgo.util.RND;

import java.time.LocalDate;
import java.util.Random;

public class ClientEntity1 {

    static Random rnd = new Random();
    static int min = 100000;
    static int max = 100000000;


    public static float createFloatNumber(float start, float end) {
        return (float) (start + Math.random() * (end - start));
    }

    public static int createRandomIntBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }

    public static LocalDate createRandomDate(int startYear, int endYear) {
        int day = createRandomIntBetween(1, 28);
        int month = createRandomIntBetween(1, 12);
        int year = createRandomIntBetween(startYear, endYear);
        return LocalDate.of(year, month, day);
    }


    public static Client getClient(Integer charmId) {
        Client client = new Client();
        client.id = min + rnd.nextInt(max - min + 1);
        client.surname = RND.str(10);
        client.name = RND.str(10);
        client.patronymic = RND.str(10);
//        client.gender = Client.gender.MALE;
        client.bDate = createRandomDate(1000, 1900);
        client.charm = charmId;
        return client;
    }

    public static Charm getCharm() {
        Charm charm = new Charm();
        charm.id = min + rnd.nextInt(max - min + 1);
        charm.name = RND.str(10);
        charm.description = RND.str(10);
        charm.energy = createFloatNumber(1, 10);
        return charm;
    }


    public static String extractURIByRel(final String linkHeader, final String rel) {
        if (linkHeader == null) {
            return null;
        }

        String uriWithSpecifiedRel = null;
        final String[] links = linkHeader.split(", ");
        String linkRelation = null;
        for (final String link : links) {
            final int positionOfSeparator = link.indexOf(';');
            linkRelation = link.substring(positionOfSeparator + 1, link.length()).trim();
            if (extractTypeOfRelation(linkRelation).equals(rel)) {
                uriWithSpecifiedRel = link.substring(1, positionOfSeparator - 1);
                break;
            }
        }

        return uriWithSpecifiedRel;
    }

    static Object extractTypeOfRelation(final String linkRelation) {
        final int positionOfEquals = linkRelation.indexOf('=');
        return linkRelation.substring(positionOfEquals + 2, linkRelation.length() - 1).trim();
    }

    public static String filterByAttr(Filter filter){
        String sql = "";
        if(filter.name!=null || filter.name!=""){ sql+=" LOWER(c.name) like LOWER(#{filter.name}) || '%'"; }
        if(filter.patronymic!=null || filter.patronymic!=""){ sql+=" LOWER(c.patronymic) like LOWER(#{filter.patronymic}) || '%'"; }
        if(filter.surname!=null || filter.surname!=""){ sql+=" LOWER(c.patronymic) like LOWER(#{filter.patronymic}) || '%'";}

        return sql;
    }
    public static String orderBy(Filter filter){
        String sql ="";
        if(filter.sort!=null || )
    }
}
