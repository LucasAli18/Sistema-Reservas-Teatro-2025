import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import clienteService from '../services/clienteService';
import reservaService from '../services/reservaService';

export default function ReservasPorCliente() {
  const { dni } = useParams();
  const [reservas, setReservas] = useState([]);
  const [editarId, setEditarId] = useState(null);
  const [nuevoEstado, setNuevoEstado] = useState('');

  const cargarReservas = () => {
    clienteService.getReservasPorCliente(dni).then(setReservas);
  };

  useEffect(() => {
    cargarReservas();
  }, [dni]);

  const eliminarReserva = (id) => {
    reservaService.eliminarReserva(id).then(cargarReservas);
  };

  const actualizarEstado = () => {
    if (editarId && nuevoEstado) {
      clienteService.editarEstadoReserva(editarId, nuevoEstado).then(() => {
        setEditarId(null);
        setNuevoEstado('');
        cargarReservas();
      });
    }
  };

  return (
    <div>
      <h2>Reservas del Cliente</h2>
      <ul>
        {reservas.map(reserva => (
          <li key={reserva.id}>
            Evento: {reserva.evento.nombre} - {reserva.evento.fecha} {reserva.evento.hora} <br />
            Entrada: {reserva.tipoEntrada} - Estado: {reserva.estadoPago}
            <button onClick={() => eliminarReserva(reserva.id)}>Eliminar</button>
            <button onClick={() => { setEditarId(reserva.id); setNuevoEstado(reserva.estadoPago); }}>Editar Estado</button>
          </li>
        ))}
      </ul>

      {editarId && (
        <div>
          <h4>Editar estado de la reserva</h4>
          <select value={nuevoEstado} onChange={(e) => setNuevoEstado(e.target.value)}>
            <option value="PAGADO">PAGADO</option>
            <option value="NO_PAGADO">NO_PAGADO</option>
          </select>
          <button onClick={actualizarEstado}>Actualizar</button>
        </div>
      )}
    </div>
  );
}