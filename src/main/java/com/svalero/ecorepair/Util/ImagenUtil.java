package com.svalero.ecorepair.util;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Component
public class ImagenUtil {

    private static final List<String> EXTENSIONES_PERMITIDAS = List.of("jpg", "jpeg", "png", "gif", "webp");
    private static final long TAMANIO_MAXIMO = 15_000_000L;
    private final Cloudinary cloudinary;
    private final String carpeta;

    public ImagenUtil(
            @Value("${cloudinary.cloud-name:}") String cloudName,
            @Value("${cloudinary.api-key:}") String apiKey,
            @Value("${cloudinary.api-secret:}") String apiSecret,
            @Value("${cloudinary.folder:ecorepair}") String carpeta) {
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret
        ));
        this.carpeta = carpeta;
    }

    public boolean validarImagen(MultipartFile archivo) {
        if (archivo == null || archivo.isEmpty()) return false;
        if (archivo.getSize() > TAMANIO_MAXIMO) return false;

        String nombre = archivo.getOriginalFilename();
        if (nombre == null || !nombre.contains(".")) return false;

        String extension = nombre.substring(nombre.lastIndexOf(".") + 1).toLowerCase();
        return EXTENSIONES_PERMITIDAS.contains(extension);
    }

    public String procesarImagen(MultipartFile archivo) {
        if (archivo == null || archivo.isEmpty()) {
            throw new IllegalArgumentException("Image is required");
        }
        if (!validarImagen(archivo)) {
            throw new IllegalArgumentException("Invalid image. Use jpg, jpeg, png, gif or webp. Max 15MB");
        }
        try {
            Map resultado = cloudinary.uploader().upload(
                    archivo.getBytes(),
                    ObjectUtils.asMap("folder", carpeta)
            );
            return (String) resultado.get("secure_url");
        } catch (Exception e) {
            throw new RuntimeException("Error saving image: " + e.getMessage());
        }
    }

    public void eliminarImagen(String url) {
        if (url == null || url.isBlank()) return;
        try {
            String publicId = url
                    .replaceAll(".*/upload/v\\d+/", "")
                    .replaceAll("\\.[^.]+$", "");
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (Exception ignored) {}
    }
}
