package com.ramotion.directselect.examples.simple;

import com.ramotion.directselect.R;

import java.util.Arrays;
import java.util.List;

public class AdvancedExampleDataPOJO {

    private String title;
    private String subTitle;
    private int icon;

    public AdvancedExampleDataPOJO(String title, int icon) {
        this.title = title;
        this.icon = icon;
    }

    public AdvancedExampleDataPOJO(String title, String subTitle, int icon) {
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

    public static List<AdvancedExampleDataPOJO> getExampleDataset() {
        return Arrays.asList(
                new AdvancedExampleDataPOJO("Atlanta Hawks", R.drawable.ds_nba_atlanta_hawks),
                new AdvancedExampleDataPOJO("Boston Celtics", R.drawable.ds_nba_boston_celtics),
                new AdvancedExampleDataPOJO("Brooklyn Nets", R.drawable.ds_nba_brooklyn_nets),
                new AdvancedExampleDataPOJO("Charlotte Hornets", R.drawable.ds_nba_charlotte_hornets),
                new AdvancedExampleDataPOJO("Chicago Bulls", R.drawable.ds_nba_chicago_bulls),
                new AdvancedExampleDataPOJO("Cleveland Cavaliers", R.drawable.ds_nba_cleveland_cavaliers),
                new AdvancedExampleDataPOJO("Dallas Mavericks", R.drawable.ds_nba_dallas_mavericks),
                new AdvancedExampleDataPOJO("Denver Nuggets", R.drawable.ds_nba_denver_nuggets),
                new AdvancedExampleDataPOJO("Detroit Pistons", R.drawable.ds_nba_detroit_pistons),
                new AdvancedExampleDataPOJO("Golden State Warriors", R.drawable.ds_nba_gs_warriors),
                new AdvancedExampleDataPOJO("Houston Rockets", R.drawable.ds_nba_houston_rockets),
                new AdvancedExampleDataPOJO("Indiana Pacers", R.drawable.ds_nba_indiana_pacers),
                new AdvancedExampleDataPOJO("Los Angeles Clippers", R.drawable.ds_nba_la_clippers),
                new AdvancedExampleDataPOJO("Los Angeles Lakers", R.drawable.ds_nba_la_lakers),
                new AdvancedExampleDataPOJO("Memphis Grizzlies", R.drawable.ds_nba_memphis_grizzlies),
                new AdvancedExampleDataPOJO("Miami Heat", R.drawable.ds_nba_miami_heat),
                new AdvancedExampleDataPOJO("Milwaukee Bucks", R.drawable.ds_nba_milwaukee_bucks),
                new AdvancedExampleDataPOJO("Minnesota Timberwolves", R.drawable.ds_nba_minnesota_timberwolves),
                new AdvancedExampleDataPOJO("New Orleans Pelicans", R.drawable.ds_nba_new_orleans_pelicans),
                new AdvancedExampleDataPOJO("New York Knicks", R.drawable.ds_nba_new_york_knicks),
                new AdvancedExampleDataPOJO("Oklahoma City Thunder", R.drawable.ds_nba_oklahoma_city_thunder),
                new AdvancedExampleDataPOJO("Orlando Magic", R.drawable.ds_nba_orlando_magic),
                new AdvancedExampleDataPOJO("Philadelphia 76ers", R.drawable.ds_nba_philadelphia_76ers),
                new AdvancedExampleDataPOJO("Phoenix Suns", R.drawable.ds_nba_phoenix_suns),
                new AdvancedExampleDataPOJO("Portland Trail Blazers", R.drawable.ds_nba_portland_trail_blazers),
                new AdvancedExampleDataPOJO("Sacramento Kings", R.drawable.ds_nba_sacramento_kings),
                new AdvancedExampleDataPOJO("San Antonio Spruts", R.drawable.ds_nba_san_antonio_spruts),
                new AdvancedExampleDataPOJO("Toronto Raptors", R.drawable.ds_nba_toronto_raptors),
                new AdvancedExampleDataPOJO("Utah Jazz", R.drawable.ds_nba_utah_jazz),
                new AdvancedExampleDataPOJO("Washington Wizards", R.drawable.ds_nba_washington_wizards)
        );
    }
}
