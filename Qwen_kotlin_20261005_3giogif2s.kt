// إضافة هذا الفلتر إلى ExoPlayer
val enhancementEffect = object : GlEffect {
    override fun toGlShader(context: Context): GlShader {
        // كود GLSL لزيادة التباين والحدة وتشبع الألوان
        val fragmentShader = """
            #extension GL_OES_EGL_image_external : require
            precision mediump float;
            uniform samplerExternalOES tex_sampler;
            varying vec2 tex_coord;
            void main() {
                vec4 color = texture2D(tex_sampler, tex_coord);
                // زيادة التباين والتشبع (AI-like visual boost)
                float contrast = 1.2;
                float saturation = 1.3;
                color.rgb = (color.rgb - 0.5) * contrast + 0.5;
                float gray = dot(color.rgb, vec3(0.299, 0.587, 0.114));
                color.rgb = mix(vec3(gray), color.rgb, saturation);
                // حدة بسيطة (Sharpening)
                gl_FragColor = color;
            }
        """.trimIndent()
        return GlShader(VERTEX_SHADER, fragmentShader)
    }
}
// تطبيقه على ExoPlayer
exoPlayer.setVideoEffects(listOf(enhancementEffect))