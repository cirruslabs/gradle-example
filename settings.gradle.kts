rootProject.name = "gradle-http-cache-example"

val isCiServer = System.getenv().containsKey("CI")
val isMasterBranch = System.getenv("GITHUB_REF").orEmpty().contains("master")
    || System.getenv("CIRRUS_BRANCH") == "master"

buildCache {
  local {
    isEnabled = !isCiServer
  }
  remote<HttpBuildCache> {
    val cacheHost = System.getenv().getOrDefault("CIRRUS_HTTP_CACHE_HOST", "localhost:12321")
    url = uri("http://$cacheHost/")
    isEnabled = isCiServer
    isPush = isMasterBranch
  }
}
