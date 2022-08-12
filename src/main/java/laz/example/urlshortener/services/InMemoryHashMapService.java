package laz.example.urlshortener.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class InMemoryHashMapService {

    private final Map<String, String> inMemoryURLDatabase = new ConcurrentHashMap<>();

    private final CrumbService crumbService;

    @Autowired
    public InMemoryHashMapService(final CrumbService crumbService) {
        this.crumbService = crumbService;
    }

    /**
     * Generate a crumb and store the URL against the crumb for later retrieval.
     * Crumb length can in theory can be up to the JVM crashes.
     * In reality though will start at 3, then when conflicts start occurring will move onto 4, then 5, and so on,
     * as all Crumbs begin to get uniquely filled.
     *
     * @param fullUrl - The url you want to crumb. No attempt is made to validate the url.
     * @return the new crumb for the URL.
     */
    public String genAndStoreURLForCrumb(final String fullUrl) {
        // TODO configure the initial size?
        int initialSize = 3;
        String crumb = this.crumbService.getCrumb(initialSize);

        // Get a unique key
        while (this.inMemoryURLDatabase.containsKey(crumb)) {
            crumb = this.crumbService.getCrumb(initialSize++);
        }

        this.inMemoryURLDatabase.put(crumb, fullUrl);

        return crumb;
    }

    /**
     * Get the stored full URL if one exists for the given crumb.
     *
     * @param crumb - The given crumb.
     * @return the fullURL.
     */
    public Optional<String> getCrumb(final String crumb) {
        if (!this.inMemoryURLDatabase.containsKey(crumb)) {
            return Optional.empty();
        }
        return Optional.of(this.inMemoryURLDatabase.get(crumb));
    }
}