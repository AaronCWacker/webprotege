package edu.stanford.bmir.protege.web.server.form;

import edu.stanford.bmir.protege.web.server.jackson.ObjectMapperProvider;
import edu.stanford.bmir.protege.web.server.persistence.MongoTestUtils;
import edu.stanford.bmir.protege.web.shared.form.FormDescriptor;
import edu.stanford.bmir.protege.web.shared.form.FormId;
import edu.stanford.bmir.protege.web.shared.form.field.*;
import edu.stanford.bmir.protege.web.shared.lang.LanguageMap;
import edu.stanford.bmir.protege.web.shared.project.ProjectId;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2019-11-01
 */
public class EntityFormRepositoryImpl_IT {

    private EntityFormRepositoryImpl impl;

    @Before
    public void setUp() {
        var mongoClient = MongoTestUtils.createMongoClient();
        var database = mongoClient.getDatabase(MongoTestUtils.getTestDbName());

        var objectMapperProvider = new ObjectMapperProvider();
        var objectMapper = objectMapperProvider.get();
        impl = new EntityFormRepositoryImpl(objectMapper, database);
    }

    @Test
    public void shouldSaveFormDescriptor() {

        var languageMap = LanguageMap.builder()
                                     .put("en", "Water")
                                     .put("de", "Wasser")
                                     .put("en-US", "Water")
                                     .build();
        var formId = new FormId("TonicWater");
        var formDescriptor = FormDescriptor.builder(formId)
                      .addDescriptor(FormElementDescriptor.get(
                              FormElementId.get("Brand"),
                              languageMap,
                              ElementRun.START,
                              new TextFieldDescriptor(
                                      LanguageMap.of("en", "Enter brand name"),
                                      StringType.SIMPLE_STRING,
                                      LineMode.SINGLE_LINE,
                                      "Pattern",
                                      LanguageMap.of("en", "There's an error")
                              ),
                              Repeatability.NON_REPEATABLE,
                              Optionality.OPTIONAL,
                              LanguageMap.of("en", "Help text")
                      ))
                      .build();

        var projectId = ProjectId.get("12345678-1234-1234-1234-123456789abc");
        impl.saveFormDescriptor(projectId,
                                formDescriptor);
        var deserializedFormDescriptor = impl.findFormDescriptor(projectId, formId);
        assertThat(deserializedFormDescriptor.isPresent(), is(true));
        assertThat(deserializedFormDescriptor.get(), is(formDescriptor));
    }
}
