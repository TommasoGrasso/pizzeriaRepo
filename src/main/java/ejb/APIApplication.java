package ejb;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class APIApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> set = new HashSet<>();
        set.add( ImpastoService.class );
        set.add( IngredienteService.class );
        set.add( PizzaService.class );
        set.add( UserService.class );
        return set;
    }
}