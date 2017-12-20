package com.ramotion.directselect.examples.simple;

import com.ramotion.directselect.R;

import java.util.Arrays;
import java.util.List;

public class SimpleExampleDataPOJO {

    private String title;
    private String subTitle;
    private int icon;

    public SimpleExampleDataPOJO(String title, int icon) {
        this.title = title;
        this.icon = icon;
    }

    public SimpleExampleDataPOJO(String title, String subTitle, int icon) {
        this.title = title;
        this.subTitle = subTitle;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public static List<SimpleExampleDataPOJO> getExampleDataset() {
        return Arrays.asList(
                new SimpleExampleDataPOJO("Atlanta Hawks", R.drawable.ds_nba_atlanta_hawks),
                new SimpleExampleDataPOJO("Boston Celtics", R.drawable.ds_nba_boston_celtics),
                new SimpleExampleDataPOJO("Brooklyn Nets", R.drawable.ds_nba_brooklyn_nets),
                new SimpleExampleDataPOJO("Charlotte Hornets", R.drawable.ds_nba_charlotte_hornets),
                new SimpleExampleDataPOJO("Chicago Bulls", R.drawable.ds_nba_chicago_bulls),
                new SimpleExampleDataPOJO("Cleveland Cavaliers", R.drawable.ds_nba_cleveland_cavaliers),
                new SimpleExampleDataPOJO("Dallas Mavericks", R.drawable.ds_nba_dallas_mavericks),
                new SimpleExampleDataPOJO("Denver Nuggets", R.drawable.ds_nba_denver_nuggets),
                new SimpleExampleDataPOJO("Detroit Pistons", R.drawable.ds_nba_detroit_pistons),
                new SimpleExampleDataPOJO("Golden State Warriors", R.drawable.ds_nba_gs_warriors),
                new SimpleExampleDataPOJO("Houston Rockets", R.drawable.ds_nba_houston_rockets),
                new SimpleExampleDataPOJO("Indiana Pacers", R.drawable.ds_nba_indiana_pacers),
                new SimpleExampleDataPOJO("Los Angeles Clippers", R.drawable.ds_nba_la_clippers),
                new SimpleExampleDataPOJO("Los Angeles Lakers", R.drawable.ds_nba_la_lakers),
                new SimpleExampleDataPOJO("Memphis Grizzlies", R.drawable.ds_nba_memphis_grizzlies),
                new SimpleExampleDataPOJO("Miami Heat", R.drawable.ds_nba_miami_heat),
                new SimpleExampleDataPOJO("Milwaukee Bucks", R.drawable.ds_nba_milwaukee_bucks),
                new SimpleExampleDataPOJO("Minnesota Timberwolves", R.drawable.ds_nba_minnesota_timberwolves),
                new SimpleExampleDataPOJO("New Orleans Pelicans", R.drawable.ds_nba_new_orleans_pelicans),
                new SimpleExampleDataPOJO("New York Knicks", R.drawable.ds_nba_new_york_knicks),
                new SimpleExampleDataPOJO("Oklahoma City Thunder", R.drawable.ds_nba_oklahoma_city_thunder),
                new SimpleExampleDataPOJO("Orlando Magic", R.drawable.ds_nba_orlando_magic),
                new SimpleExampleDataPOJO("Philadelphia 76ers", R.drawable.ds_nba_philadelphia_76ers),
                new SimpleExampleDataPOJO("Phoenix Suns", R.drawable.ds_nba_phoenix_suns),
                new SimpleExampleDataPOJO("Portland Trail Blazers", R.drawable.ds_nba_portland_trail_blazers),
                new SimpleExampleDataPOJO("Sacramento Kings", R.drawable.ds_nba_sacramento_kings),
                new SimpleExampleDataPOJO("San Antonio Spruts", R.drawable.ds_nba_san_antonio_spruts),
                new SimpleExampleDataPOJO("Toronto Raptors", R.drawable.ds_nba_toronto_raptors),
                new SimpleExampleDataPOJO("Utah Jazz", R.drawable.ds_nba_utah_jazz),
                new SimpleExampleDataPOJO("Washington Wizards", R.drawable.ds_nba_washington_wizards)
        );
    }
}
