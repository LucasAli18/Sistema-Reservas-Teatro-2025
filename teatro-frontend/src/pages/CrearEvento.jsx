import { useEffect, useState } from 'react';
import eventoService from '../services/eventoService';
import reservaService from '../services/reservaService';
import { useNavigate } from 'react-router-dom';

export default function CrearEvento() {
  const [eventos, setEventos] = useState([]);
  const [nuevo, setNuevo] = useState({ id: '', nombre: '', fecha: '', hora: '', capacidadMaxima: '', tipoEvento: '' });
  const [modoEdicion, setModoEdicion] = useState(false);
  const [editarReservaId, setEditarReservaId] = useState(null);
  const [estadoEditado, setEstadoEditado] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    eventoService.getEventos().then(setEventos);
  }, []);

  const cargarEventos = () => eventoService.getEventos().then(setEventos);

  const crear = () => {
    eventoService.crearEvento(nuevo).then(() => {
      cargarEventos();
      setNuevo({ id: '', nombre: '', fecha: '', hora: '', capacidadMaxima: '', tipoEvento: '' });
    });
  };

  const editar = () => {
    eventoService.editarEvento(nuevo.id, nuevo).then(() => {
      cargarEventos();
      setNuevo({ id: '', nombre: '', fecha: '', hora: '', capacidadMaxima: '', tipoEvento: '' });
      setModoEdicion(false);
    });
  };

  const eliminar = (id) => {
    eventoService.eliminarEvento(id).then(cargarEventos);
  };

  const cargarParaEditar = (evento) => {
    setNuevo({ ...evento });
    setModoEdicion(true);
  };

  const actualizarEstadoReserva = () => {
    if (editarReservaId && estadoEditado) {
      reservaService.editarEstadoReserva(editarReservaId, estadoEditado).then(() => {
        setEditarReservaId(null);
        setEstadoEditado('');
      });
    }
  };

  return (
    <div>
      <h2>Eventos</h2>
      <form onSubmit={(e) => { e.preventDefault(); modoEdicion ? editar() : crear(); }}>
        <input required placeholder="Nombre" value={nuevo.nombre} onChange={e => setNuevo({ ...nuevo, nombre: e.target.value })} />
        <input required type="date" value={nuevo.fecha} onChange={e => setNuevo({ ...nuevo, fecha: e.target.value })} />
        <input required type="time" value={nuevo.hora} onChange={e => setNuevo({ ...nuevo, hora: e.target.value })} />
        <input required type="number" placeholder="Capacidad" value={nuevo.capacidadMaxima} onChange={e => setNuevo({ ...nuevo, capacidadMaxima: e.target.value })} />
        <select required value={nuevo.tipoEvento} onChange={e => setNuevo({ ...nuevo, tipoEvento: e.target.value })}>
          <option value="">Seleccionar tipo</option>
          <option value="OBRA">OBRA</option>
          <option value="RECITAL">RECITAL</option>
          <option value="CHARLA">CHARLA</option>
        </select>
        <button type="submit">{modoEdicion ? 'Actualizar' : 'Crear'}</button>
      </form>

      <ul>
        {eventos.map(e => (
          <li key={e.id}>
           {e.nombre} <br />Fecha: {e.fecha} <br /> Hora: {e.hora} <br /> Disponible: {e.capacidadMaxima - e.reservasRealizadas} Lugares - Tipo: {e.tipoEvento}{' '}
            <button onClick={() => navigate(`/eventos/${e.id}/reservas`)}>Ver Reservas</button>
            <button onClick={() => cargarParaEditar(e)}>Editar</button>
            <button onClick={() => eliminar(e.id)}>Eliminar</button>
          </li>
        ))}
      </ul>

      {editarReservaId && (
        <div>
          <h4>Editar estado de la reserva</h4>
          <select value={estadoEditado} onChange={(e) => setEstadoEditado(e.target.value)}>
            <option value="PAGADO">PAGADO</option>
            <option value="NO_PAGADO">NO_PAGADO</option>
          </select>
          <button onClick={actualizarEstadoReserva}>Actualizar estado</button>
        </div>
      )}
    </div>
  );
}