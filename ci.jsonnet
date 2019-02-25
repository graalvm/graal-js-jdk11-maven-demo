{
  local basicBuild = {
    targets: ['gate'],
    packages: {
      maven: '==3.3.9',
    },
    timelimit: '00:59:59',
    run: [
      ['mvn', 'clean'],
      ['mvn', 'package'],
      ['mvn', 'exec:exec'],
    ],
  },

  local jdk8 = {
    downloads+: {
      JAVA_HOME: { name: 'oraclejdk', version: '8u202', platformspecific: true },
    },
  },

  local jdk11 = {
    run+: [
      ['mvn', 'exec:exec@nograal'],
    ],
    downloads+: {
      JAVA_HOME: { name: 'oraclejdk', version: '11+20', platformspecific: true },
    },
  },

  local linux = {
    capabilities+: ['linux', 'amd64'],
  },

  local darwin = {
    capabilities+: ['darwin_sierra', 'amd64'],
    environment+: {
      MACOSX_DEPLOYMENT_TARGET: '10.11',
    },
  },

  builds: [
    basicBuild + linux + jdk8 + { name: 'linux-jdk8' },
    basicBuild + linux + jdk11 + { name: 'linux-jdk11' },

    basicBuild + darwin + jdk8 + { name: 'darwin-jdk8' },
    basicBuild + darwin + jdk11 + { name: 'darwin-jdk11' },
  ],
}
