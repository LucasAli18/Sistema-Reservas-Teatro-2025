import { useEffect, useState } from 'react';
import reservaService from '../services/reservaService';
import clienteService from '../services/clienteService';
import eventoService from '../services/eventoService';

export default function CrearReserva() {
  const [reserva, setReserva] = useState({ dniCliente: '', idEvento: '', tipoEntrada: '', estadoPago: 'NO_PAGADO' });
  const [reservas, setReservas] = useState([]);
  const [clientes, setClientes] = useState([]);
  const [eventos, setEventos] = useState([]);
  const [tipoEntradaOptions, setTipoEntradaOptions] = useState([]);

  useEffect(() => {
    reservaService.getReservas().then(setReservas);
    clienteService.getClientes().then(setClientes);
    eventoService.getEventos().then(setEventos);
  }, []);

  const handleEventoChange = (e) => {
    const idEvento = e.target.value;
    const evento = eventos.find(ev => ev.id.toString() === idEvento);
    if (!evento) return;

    let opciones = [];
    if (evento.tipoEvento === 'OBRA') {
      opciones = ['GENERAL', 'VIP'];
    } else if (evento.tipoEvento === 'RECITAL') {
      opciones = ['CAMPO', 'PLATEA', 'PALCO'];
    } else if (evento.tipoEvento === 'CHARLA') {
      opciones = ['GENERAL', 'MEET_AND_GREET'];
    }
    setTipoEntradaOptions(opciones);
    setReserva({ ...reserva, idEvento, tipoEntrada: '' });
  };

  const crear = () => {
    reservaService.crearReserva(reserva).then(() => {
      reservaService.getReservas().then(setReservas);
      setReserva({ dniCliente: '', idEvento: '', tipoEntrada: '', estadoPago: 'NO_PAGADO' });
      setTipoEntradaOptions([]);
    });
  };

  return (
    <div>
      <h2>Crear Reserva</h2>
      <form onSubmit={(e) => { e.preventDefault(); crear(); }}>
        <select required value={reserva.dniCliente} onChange={e => setReserva({ ...reserva, dniCliente: e.target.value })}>
          <option value="">Seleccionar Cliente</option>
          {clientes.map(c => (
            <option key={c.dni} value={c.dni}>{c.nombre}</option>
          ))}
        </select>

        <select required value={reserva.idEvento} onChange={handleEventoChange}>
          <option value="">Seleccionar Evento</option>
          {eventos.map(e => (
            <option key={e.id} value={e.id}>{e.nombre} - {e.fecha} - {e.hora}</option>
          ))}
        </select>

        <select required value={reserva.tipoEntrada} onChange={e => setReserva({ ...reserva, tipoEntrada: e.target.value })}>
          <option value="">Seleccionar Entrada</option>
          {tipoEntradaOptions.map(op => (
            <option key={op} value={op}>{op}</option>
          ))}
        </select>

        <select required value={reserva.estadoPago} onChange={e => setReserva({ ...reserva, estadoPago: e.target.value })}>
          <option value="PAGADO">PAGADO</option>
          <option value="NO_PAGADO">NO_PAGADO</option>
        </select>

        <button type="submit">Reservar</button>
      </form>

      <h3>Reservas existentes</h3>
      <ul>
        {reservas.map((reserva, idx) => (
          <li key={idx}> Cliente: {reserva.cliente?.nombre} - DNI: {reserva.cliente?.dni} 
          <br />  
          Evento: {reserva.evento?.nombre} - {reserva.tipoEntrada} - Estado: {reserva.estadoPago}
          <br />
          Fecha: {reserva.evento.fecha} - Hora: {reserva.evento.hora}</li>
        ))}
      </ul>
    </div>
  );
}