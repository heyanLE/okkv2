### OKKV2 使用文档

[返回目录](./menu.md)

#### 1.依赖导入

##### 加入 jitpack 依赖源：

项目根目录的 build.gradle(.kts) 文件：

groovy:
```groovy
buildscript {
    repositories {
        // other
        maven { url 'https://jitpack.io' }
    }
}
```

kotlin:
```kotlin
buildscript {
    repositories {
        // other
        maven { url = uri("https://jitpack.io") }
    }
}
```

如果加入后无效果或者报错，可以看看项目根目录的 setting.gradle(.kts) 文件：

```groovy
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        // other
        maven {url 'https://jitpack.io'}
    }
}
```

```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        // other
        maven { url = uri("https://jitpack.io") }
    }
}
```

##### 在 module 中加入依赖：

[![](https://jitpack.io/v/heyanLE/okkv2.svg)](https://jitpack.io/#heyanLE/okkv2)

版本请更换为以上最新版本

groovy:

```groovy
implementation 'com.github.heyanLE.okkv2:okkv2-core:1.0.9'
implementation 'com.github.heyanLE.okkv2:okkv2-mmkv:1.0.9' // 使用 mmkv 储存
implementation 'com.github.heyanLE.okkv2:okkv2-sp:1.0.9' // 使用 sharedPreferences 储存
```

kotlin:

```kotlin
implementation("com.github.heyanLE.okkv2:okkv2-core:1.0.9")
implementation("com.github.heyanLE.okkv2:okkv2-mmkv:1.0.9") // 使用 mmkv 储存
implementation("com.github.heyanLE.okkv2:okkv2-sp:1.0.9") // 使用 sharedPreferences 储存
```