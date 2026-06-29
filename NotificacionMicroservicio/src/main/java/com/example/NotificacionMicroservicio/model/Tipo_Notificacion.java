package com.example.NotificacionMicroservicio.model;

public enum Tipo_Notificacion { // Define las categorías o niveles de urgencia de los mensajes del sistema
    INFO, // Mensajes generales (ej. "Bienvenido a la biblioteca", "Nuevo catálogo disponible")
    ALERTA, // Avisos urgentes o problemas (ej. "Tu préstamo vence hoy a las 18:00 hrs")
    MULTA, // Categoría exclusiva para informar sobre castigos económicos generados
    RECORDATORIO // Mensajes de seguimiento preventivo (ej. "Recuerda devolver tu libro mañana")
}
