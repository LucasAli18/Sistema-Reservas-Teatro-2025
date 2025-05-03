import { useEffect, useState } from 'react';
import eventoService from '../services/eventoService';
import { useNavigate } from 'react-router-dom';

export default function Home() {
  const [eventos, setEventos] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    eventoService.getEventos().then(setEventos);
  }, []);

  const eventosOrdenados = [...eventos].sort((a, b) => {
    const fechaA = new Date(`${a.fecha}T${a.hora}`);
    const fechaB = new Date(`${b.fecha}T${b.hora}`);
    if (fechaA - fechaB !== 0) return fechaA - fechaB;
    return b.capacidadMaxima - a.capacidadMaxima;
  });

  return (
    <div>
      <h1>Eventos</h1>
      <ul>
        {eventosOrdenados.map(evento => (
          <li key={evento.id}>
            {evento.nombre} - {evento.fecha} {evento.hora} - Total: {evento.capacidadMaxima} - Restante:{evento.capacidadMaxima - evento.reservasRealizadas}{' '}
            <button onClick={() => navigate(`/eventos/${evento.id}/reservas`)}>Ver Reservas</button>
          </li>
        ))}
      </ul>
    </div>
  );
}