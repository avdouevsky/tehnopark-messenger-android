package com.mshvdvskgmail.technoparkmessenger.network;

import java.util.List;

/**
 * Created by Admin on 30.01.2017.
 */

public class RouteResponse {

    public List<Route> routes;

    public String getPoints() {
        return this.routes.get(0).overview_polyline.points;
    }

    class Route {
        OverviewPolyline overview_polyline;
    }

    class OverviewPolyline {
        String points;
    }
}
