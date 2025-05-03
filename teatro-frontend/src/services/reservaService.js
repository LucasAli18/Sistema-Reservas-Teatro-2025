import axios from 'axios';
const API = 'http://localhost:8080/reservas';

export default {
  getReservas: () => axios.get(API).then(res => res.data),
  crearReserva: (reserva) => axios.post(API, reserva),
  eliminarReserva: (id) => axios.delete(`${API}/${id}`),
};