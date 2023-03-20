package loli.ball.okkv2

import com.heyanle.okkv2.core.Converter
import com.heyanle.okkv2.core.Okkv
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

/**
 * Created by LoliBall on 2023/3/20 10:14.
 * https://github.com/WhichWho
 */
class KotlinxSerializationConverter<T : Any>(private val json: Json) : Converter<T, String> {

    override fun serialize(data: T, clazz: Class<T>): String {
        return json.encodeToString(serializer(clazz), data)
    }

    @Suppress("UNCHECKED_CAST")
    override fun deserialize(data: String, clazz: Class<T>): T {
        return json.decodeFromString(serializer(clazz), data) as T
    }

}

fun Okkv.Builder.fallbackKotlinxSerializationConverter(json: Json = Json) = apply {
    fallbackConverter(KotlinxSerializationConverter(json))
}
