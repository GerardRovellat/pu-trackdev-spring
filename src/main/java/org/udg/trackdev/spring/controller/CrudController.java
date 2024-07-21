package org.udg.trackdev.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.udg.trackdev.spring.controller.exceptions.ControllerException;
import org.udg.trackdev.spring.query.CriteriaParser;
import org.udg.trackdev.spring.query.GenericSpecificationsBuilder;
import org.udg.trackdev.spring.query.SearchSpecification;
import org.udg.trackdev.spring.service.IBaseService;

import java.util.List;

/**
 * The type Crud controller.
 *
 * @param <T>       the type parameter
 * @param <Service> the type parameter
 */
public class CrudController<T, Service extends IBaseService<T>> extends BaseController {

    /**
     * The Service.
     */
    @Autowired
    protected Service service;

    /**
     * Search list.
     *
     * @param search the search
     * @return the list
     */
    public List<T> search(String search) {
        if (search == null)
            return service.getAll();
        Specification<T> specification = this.buildSpecificationFromSearch(search);
        return service.search(specification);
    }

    /**
     * Build specification from search specification.
     *
     * @param <K>    the type parameter
     * @param search the search
     * @return the specification
     */
    protected <K> Specification<K> buildSpecificationFromSearch(String search) {
        Specification<K> specification;
        try {
            CriteriaParser parser = new CriteriaParser();
            GenericSpecificationsBuilder<K> specBuilder = new GenericSpecificationsBuilder<>();
            specification = specBuilder.build(parser.parse(search), SearchSpecification::new);
        } catch (Exception ex) {
            throw new ControllerException("Error parsing search parameter");
        }
        return specification;
    }

    /**
     * Scoped search string.
     *
     * @param reducedScopeSearch the reduced scope search
     * @param requestSearch      the request search
     * @return the string
     */
    protected String scopedSearch(String reducedScopeSearch, String requestSearch) {
        return reducedScopeSearch + (requestSearch != null ? " and ( " + requestSearch + " )" : "");
    }
}
