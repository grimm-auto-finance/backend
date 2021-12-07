// layer: frameworksanddrivers
package server;

import routes.Addons;
import routes.Loan;
import routes.Route;
import routes.Search;

/** A class wrapping all the routes of the program. */
public class Routes {
    /** The routes of the program. */
    public static final Route[] routes = {
        new Loan(Env.SENSO_RATE_URL, Env.SENSO_SCORE_URL, Server.logger, Server.parser, Server.packager),
        new Search(Server.dataBase, Server.logger, Server.parser, Server.packager),
        new Addons(Server.dataBase, Server.logger, Server.parser, Server.packager),
    };
}
