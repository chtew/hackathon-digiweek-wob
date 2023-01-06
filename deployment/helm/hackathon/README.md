# Helm chart for TrafficAnalysis

This is the Helm chart to deploy traffic analysis app. 

The following command deploys the app + MariaDB as well as a preconfigured Grafana. Copy values.yaml and adapt to your target environment needs. This expects a running cert manager - you are able to run it without, but do not delete tls values. Please change hostname in your custom values - localhost does not work. (No also not on your dev machine :)). 

```console
$ helm install release-name . -f your-customvalues.yaml
```
A running keycloak is expected. In fact it is supposed, that for every deployment you choose a proper context path, such that multiple versions of this software can be deployed to the same Kubernetes clsuter and the same domain.

This Helm chart also requires a pull secret to pull latest image from Github. Secret name is exptected as `github-pull-secret`. Create secret like below:

```
kubectl create secret docker-registry github-pull-secret --docker-server=ghcr.io --docker-username=<<your github user>> --docker-password=<<your github PAT>> --docker-email=<<your github email>> --namespace=city
```
That should the following secret:

```YAML
kind: Secret
type: kubernetes.io/dockerconfigjson
apiVersion: v1
metadata:
  name: github-pull-secret
  labels:
    app: app-name
data:
  .dockerconfigjson: base64'ed string
```

Stuff in `dockerconfigjson` field is a config file encoded in base64 and can be done like so:

```console
echo -n '{"auths":{"ghcr.io":{"auth":"yourcredentials"}}}' | base64
```
Finaly string in auth is username + Github access token again base64 encoded. Sample command:

```console
echo -n "username:token" | base64
```