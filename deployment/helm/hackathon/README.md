# Helm chart for City.OS
This is the Helm chart to deploy City.OS app. 

## TL;DR;

The following command deploys City.OS + MariaDB and authentication with Keycloak + PostrgeSQL. 

```console
$ helm install release-name . -f your-customvalues.yaml
```
Please change hostname in your custom values - localhost does not work. (No also not your dev machine :)) For more details how to use Helm please refer to Helm [docs](https://helm.sh/docs/).

## Intro
Copy values.yaml and adapt to your target environment needs. This chart expects a running cert manager, so make sure your Kubernetes (K3s,...) has a running instance. 

Also note that Keycloak is not deployed under its default context path. In fact it is supposed, that for every deployment you choose a proper context path, such that multiple versions of this software can be deployed to the same Kubernetes clsuter and the same domain.

This Helm chart also requires a pull secret to pull latest image from Github. Secret name is exptected as `github-pull-secret`, see below for an example.

## Examples

### Pull Secret
If you don't know, how to create secrets in Kubernetes, please google how to do so.

Sample:
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