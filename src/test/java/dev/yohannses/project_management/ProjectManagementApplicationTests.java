package dev.yohannses.project_management;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

@SpringBootTest
class ProjectManagementApplicationTests {

	@Test
	void test_modularity() {
        ApplicationModules modules = ApplicationModules.of(ProjectManagementApplication.class);
        modules.verify();
        new Documenter(modules).writeDocumentation().writeIndividualModulesAsPlantUml();
	}

}
