package org.jenkinsci.plugins.emailextrecipientscolumn;

import hudson.Extension;
import hudson.model.AbstractProject;
import hudson.model.Descriptor;
import hudson.model.Job;
import hudson.plugins.emailext.ExtendedEmailPublisher;
import hudson.plugins.emailext.ExtendedEmailPublisherDescriptor;
import hudson.views.ListViewColumnDescriptor;
import hudson.views.ListViewColumn;
import net.sf.json.JSONObject;

import org.kohsuke.stapler.StaplerRequest;

public final class EmailExtRecipientsColumn extends ListViewColumn {

    /**
     * The plugin descriptor.
     */
    private static final class EmailExtRecipientsColumnDescriptor extends
            ListViewColumnDescriptor {
        @Override
        public String getDisplayName() {
            return "Email Ext Recipients Column";
        }

        @Override
        public ListViewColumn newInstance(final StaplerRequest request,
                final JSONObject formData) throws FormException {
            return new EmailExtRecipientsColumn();
        }

        @Override
        public boolean shownByDefault() {
            return false;
        }
    }

    /**
     * The plugin descriptor.
     */
    @Extension
    public static final Descriptor<ListViewColumn> DESCRIPTOR = new EmailExtRecipientsColumnDescriptor();

    @Override
    public Descriptor<ListViewColumn> getDescriptor() {
        return DESCRIPTOR;
    }

    public String getRecipients(Job<?, ?> job) {
        String recipients = "";
        if (!(job instanceof AbstractProject<?, ?>)) {
            return "Not an AbstractProject";
        }
        try {
            AbstractProject<?, ?> project = (AbstractProject<?, ?>) job;
            recipients = project.getPublishersList().get(
                    ExtendedEmailPublisher.class).recipientList;
        } catch (NullPointerException e) {
            // values could not be retrieved
            return "Disabled";
        }
        return recipients;
    }
}