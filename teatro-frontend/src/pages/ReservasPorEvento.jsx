import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import eventoService from '../services/eventoService';
import reservaService from '../services/reservaService';

export default function ReservasPorEvento() {
  const { id } = useParams();
  const [reservas, setReservas] = useState([]);
  const [evento, setEvento] = useState(null);
  const [editarId, setEditarId] = useState(null);
  const [nuevoEstado, setNuevoEstado] = useState('');

  const cargarReservas = () => {
    eventoService.getReservasPorEvento(id).then(setReservas);
    eventoService.getEvento(id).then(setEvento);
  };

  useEffect(() => {
    cargarReservas();
  }, [id]);

  const eliminarReserva = (idReserva) => {
    reservaService.eliminarReserva(idReserva).then(cargarReservas);
  };

  const actualizarEstado = () => {
    if (editarId && nuevoEstado) {
      eventoService.editarEstadoReserva(editarId, nuevoEstado).then(() => {
        setEditarId();
        setNuevoEstado('');
        cargarReservas();
      });
    }
  };

  return (
    <div>
      <h2>Reservas del Evento</h2>
      {evento && (
        <h4>{evento.nombre} - {evento.fecha} {evento.hora}</h4>
      )}
      <ul>
        {reservas.map((reserva) => (
          <li key={reserva.id}>
            {reserva.cliente?.nombre} - Entrada: {reserva.tipoEntrada} - Estado: {reserva.estadoPago}
            <button onClick={() => eliminarReserva(reserva.id)}>Eliminar</button>
            <button onClick={() => { setEditarId(reserva.id); setNuevoEstado(reserva.estadoPago); }}>Editar Estado</button>
          </li>
        ))}
      </ul>

      {editarId && (
        <div>
          <h4>Editar estado de la reserva</h4>
          <select value={nuevoEstado} onChange={(e) => setNuevoEstado(e.target.value)}>
            <option value='PAGADO'>PAGADO</option>
            <option value="NO_PAGADO">NO_PAGADO</option>
          </select>
          <button onClick={actualizarEstado}>Actualizar</button>
        </div>
      )}
    </div>
  );
}