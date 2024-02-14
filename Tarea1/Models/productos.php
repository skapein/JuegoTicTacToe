<?php
// Incluir el archivo de conexión
require_once 'conexion.php';

// Verificar si se envió el formulario de eliminación
if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_POST["eliminar"])) {
    // Obtener el ID del producto a eliminar
    $id = $_POST["id"];

    // Preparar la consulta SQL para eliminar el producto
    $sql = "DELETE FROM productos WHERE id=?";
    
    // Preparar la sentencia
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("i", $id);
    
    // Ejecutar la sentencia
    if ($stmt->execute()) {
        echo "Producto eliminado exitosamente.";
    } else {
        echo "Error al eliminar el producto: " . $stmt->error;
    }
}

// Cerrar la conexión
$conn->close();
?>

<!-- Formulario para eliminar productos -->
<h2>Eliminar Producto</h2>
<form action="<?php echo $_SERVER["PHP_SELF"]; ?>" method="post">
    <label for="id">ID del Producto a Eliminar:</label>
    <input type="number" id="id" name="id" required><br><br>
    <button type="submit" name="eliminar">Eliminar Producto</button>
</form>
