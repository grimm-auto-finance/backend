package server;

import routes.Loan;
import routes.Route;
import routes.Search;

/** A class wrapping all the routes of the program. */
public class Routes {
    /** The routes of the program. */
    public static final Route[] routes = {
        new Loan(Env.SENSO_RATE_URL, Env.SENSO_SCORE_URL), new Search(Env.POSTGRES_PASSWORD),
    };
}
