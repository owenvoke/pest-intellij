package com.pestphp.pest.configuration;

import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.util.TextFieldCompletionProvider;
import com.jetbrains.php.lang.PhpFileType;
import com.jetbrains.php.testFramework.run.*;
import com.jetbrains.php.testFramework.run.PhpTestRunnerSettings.Scope;
import com.pestphp.pest.PestFrameworkType;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.EnumMap;

public class PestRunConfiguration extends PhpTestRunConfiguration {
    private static final PhpDefaultTestRunnerSettingsValidator VALIDATOR;

    protected PestRunConfiguration(Project project, ConfigurationFactory factory, String name) {
        super(
            project,
            factory,
            name,
            PestFrameworkType.getInstance(),
            VALIDATOR,
            PestRunConfigurationHandler.getInstance(),
            PestVersionDetector.getInstance()
        );
    }

    @NotNull
    @Override
    protected PhpTestRunConfigurationSettings createSettings() {
        return new PestRunConfigurationSettings();
    }

    @Override
    public @NotNull SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {
        EnumMap<Scope, String> names = new EnumMap<>(Scope.class);
        PhpTestRunConfigurationEditor editor = this.getConfigurationEditor(names);
        editor.setRunnerOptionsDocumentation("https://pestphp.com/docs/installation");
        return editor;
    }

    @Override
    protected @NotNull TextFieldCompletionProvider createMethodFieldCompletionProvider(@NotNull PhpTestRunnerConfigurationEditor editor) {
        return new TextFieldCompletionProvider() {
            @Override
            protected void addCompletionVariants(@NotNull String text, int offset, @NotNull String prefix, @NotNull CompletionResultSet result) {
                // TODO: add completions to results object.
            }
        };
    }

    static {
        VALIDATOR = new PhpDefaultTestRunnerSettingsValidator(
            Collections.singletonList(PhpFileType.INSTANCE),
            (file, name) -> true,
            false,
            false
        );
    }
}
