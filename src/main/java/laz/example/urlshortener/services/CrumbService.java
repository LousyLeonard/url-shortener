package laz.example.urlshortener.services;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

@Service
public class CrumbService {

    public CrumbService() {
        // Do Nothing
    }

    /**
     * Generate a new crumb of a given length.
     *
     * @param length - The length to create.
     * @return the new crumb
     */
    public String getCrumb(final int length) {
        // TODO Crumbs of length too large will crash JVM
        // TODO Not cryptographically secure - do we care?
        // TODO Alphanumeric is not Base64, consider using base64 for a bit more storage
        return RandomStringUtils.randomAlphanumeric(length);
    }
}