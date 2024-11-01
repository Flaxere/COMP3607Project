package tempest_foundation;

import java.io.InputStream;

public interface Iterator {
    
    InputStream next();
    boolean hasNext();
}
