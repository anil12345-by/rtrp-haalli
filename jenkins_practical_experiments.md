# Jenkins Practical Experiments

Practical notes for the Jenkins experiments completed on Windows.

## Environment used

-   Jenkins installed as a Windows service (not running in Docker).
-   Jenkins dashboard: `http://localhost:8080`
-   Jenkins version used during the lab: `2.568.3`
-   GitHub repository used for the webhook practical:
    `https://github.com/anil12345-by/jenkins-webhook-demo`
-   ngrok was used to expose the local Jenkins server temporarily so
    GitHub could send webhook events.

> Keep Jenkins running as a Windows service while doing these
> practicals. Do not publish Jenkins passwords, API tokens, GitHub
> secrets, or ngrok authentication tokens.

------------------------------------------------------------------------

# Experiment 1 --- Create a Freestyle project

## 1. Open Jenkins

Open this URL in your browser:

``` text
http://localhost:8080
```

Sign in to Jenkins.

## 2. Create the job

1.  Click **New Item**.
2.  Enter the item name:

``` text
SE_Freestyle_Project
```

3.  Select **Freestyle project**.
4.  Click **OK**.

## 3. Add a Windows batch build step

1.  Scroll to **Build Steps**.
2.  Click **Add build step**.
3.  Select **Execute Windows batch command**.
4.  Paste:

``` bat
echo =====================================
echo JENKINS FREESTYLE PROJECT
echo Build started successfully
echo =====================================
echo Current directory:
cd
echo Build completed successfully
```

5.  Click **Save**.

## 4. Run the job

1.  Click **Build Now**.
2.  Open the build under **Build History**.
3.  Click **Console Output**.

Expected final result:

``` text
Build completed successfully
Finished: SUCCESS
```

------------------------------------------------------------------------

# Experiment 2 --- Create a Scripted Pipeline

## 1. Create the job

1.  From the Jenkins dashboard, click **New Item**.
2.  Enter:

``` text
SE_Scripted_Pipeline
```

3.  Select **Pipeline**.
4.  Click **OK**.

## 2. Add the pipeline script

Scroll to the **Pipeline** section.

Set **Definition** to `Pipeline script`, then paste:

``` groovy
node {
    stage('Preparation') {
        echo 'Preparing the workspace'
    }

    stage('Build') {
        echo 'Building the application'
    }

    stage('Testing') {
        echo 'Running tests'
    }

    stage('Deployment') {
        echo 'Deploying the application'
    }

    stage('Complete') {
        echo 'Pipeline completed successfully'
    }
}
```

Click **Save**.

## 3. Run the pipeline

1.  Click **Build Now**.
2.  Open the build in **Build History**.
3.  Click **Console Output**.
4.  Confirm that all five stages complete and the build ends with:

``` text
Finished: SUCCESS
```

------------------------------------------------------------------------

# Experiment 3 --- Configure a GitHub webhook to trigger Jenkins

This practical connects a GitHub repository to a Jenkins Freestyle job.
A push to the repository sends a webhook to Jenkins and triggers a
build.

## 1. GitHub repository

Repository used:

``` text
https://github.com/anil12345-by/jenkins-webhook-demo
```

The repository contains an `index.html` file. Make a small edit and
commit it when testing the webhook.

## 2. Create the Jenkins job

1.  In Jenkins, click **New Item**.
2.  Enter:

``` text
Webhook_Demo
```

3.  Select **Freestyle project**.
4.  Click **OK**.

## 3. Configure Git source control

In the job configuration:

1.  Find **Source Code Management**.
2.  Select **Git**.
3.  Set **Repository URL** to:

``` text
https://github.com/anil12345-by/jenkins-webhook-demo.git
```

4.  Set **Branch Specifier** to:

``` text
*/main
```

5.  If the repository is public, credentials can remain unset.

## 4. Enable the GitHub build trigger

Under **Build Triggers**, select:

``` text
GitHub hook trigger for GITScm polling
```

## 5. Add the Windows batch build step

Under **Build Steps**, select **Execute Windows batch command** and
paste:

``` bat
echo =====================================
echo GITHUB WEBHOOK DEMONSTRATION
echo GitHub repository connected!
echo Code pulled successfully!
echo Automatic build triggered!
echo =====================================
```

Click **Save**.

## 6. Make Jenkins reachable from GitHub using ngrok

GitHub cannot send a webhook directly to `localhost`. During the lab,
ngrok provided a temporary public HTTPS address that forwarded traffic
to Jenkins on port 8080.

In a separate Windows terminal, start the ngrok tunnel:

``` powershell
ngrok http 8080
```

Copy the current HTTPS forwarding address shown by ngrok. It will look
similar to:

``` text
https://YOUR-CURRENT-NGROK-ADDRESS
```

Keep the ngrok terminal open while testing. The address can change when
the tunnel is restarted.

If ngrok asks for authentication, configure your own ngrok account token
locally. Never paste the token into GitHub, this README, or a public
repository.

## 7. Add the webhook in GitHub

In the GitHub repository:

1.  Open **Settings**.
2.  Select **Webhooks**.
3.  Click **Add webhook**.
4.  Set **Payload URL** to your current ngrok HTTPS address followed by:

``` text
/github-webhook/
```

Example format (replace the hostname with the current ngrok address):

``` text
https://YOUR-CURRENT-NGROK-ADDRESS/github-webhook/
```

5.  Set **Content type** to:

``` text
application/json
```

6.  Select **Just the push event**.
7.  Ensure **Active** is checked.
8.  Click **Add webhook**.

For this lab configuration, leave the webhook secret blank unless your
instructor or security setup specifically requires a secret and Jenkins
is configured to validate it.

## 8. Test the webhook

1.  Keep Jenkins and ngrok running.
2.  Edit `index.html` in the GitHub repository.
3.  Commit and push the change to the `main` branch.
4.  Open Jenkins → `Webhook_Demo`.
5.  Check **Build History** for a newly triggered build.
6.  Open the build → **Console Output**.

The successful console output observed during the practical included:

``` text
GITHUB WEBHOOK DEMONSTRATION
GitHub repository connected!
Code pulled successfully!
Automatic build triggered!
Finished: SUCCESS
```

A successful webhook test should create a new Jenkins build after the
GitHub push, without manually clicking **Build Now**.

------------------------------------------------------------------------

# Quick reference

  Practical      Jenkins item             Type
  -------------- ------------------------ ----------------------------
  Experiment 1   `SE_Freestyle_Project`   Freestyle project
  Experiment 2   `SE_Scripted_Pipeline`   Pipeline
  Experiment 3   `Webhook_Demo`           Freestyle + GitHub webhook

## Important URLs

``` text
Jenkins dashboard:
http://localhost:8080

GitHub repository:
https://github.com/anil12345-by/jenkins-webhook-demo
```

## Troubleshooting checklist

### Jenkins page does not open

-   Check that the Jenkins Windows service is running.
-   Try `http://localhost:8080` again.
-   Port 8080 was used by Jenkins during this practical; avoid assigning
    Docker containers to host port 8080.

### Freestyle or Pipeline build fails

-   Open the failed build → **Console Output**.
-   Read the first error message.
-   Check that the correct build step or Pipeline script was saved.

### GitHub webhook does not trigger a build

-   Confirm ngrok is still running and forwarding to `localhost:8080`.
-   Confirm the GitHub webhook URL uses the current ngrok HTTPS address
    and ends in `/github-webhook/`.
-   Confirm **GitHub hook trigger for GITScm polling** is enabled on
    `Webhook_Demo`.
-   Confirm the GitHub push was made to the `main` branch.
-   Check GitHub repository **Settings → Webhooks → Recent Deliveries**
    for the delivery response.
-   Check Jenkins build history and console output.

### Security and cleanup

-   Stop the ngrok tunnel with `Ctrl+C` when the webhook practical is
    finished.
-   Do not commit Jenkins credentials, API tokens, ngrok tokens, private
    keys, or secrets to GitHub.
-   Keep Jenkins access protected; do not expose an unauthenticated
    Jenkins server publicly.
