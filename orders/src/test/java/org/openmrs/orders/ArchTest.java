package org.openmrs.orders;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("org.openmrs.orders");

        noClasses()
            .that()
                .resideInAnyPackage("org.openmrs.orders.service..")
            .or()
                .resideInAnyPackage("org.openmrs.orders.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..org.openmrs.orders.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
