import axios from 'axios';
const API = 'http://localhost:8080/eventos';

export default {
  getEventos: () => axios.get(API).then(res => res.data),
  crearEvento: (evento) => axios.post(API, evento),
  eliminarEvento: (id) => axios.delete(`${API}/${id}`),
  editarEvento: (id, data) => axios.put(`${API}/${id}`, data),
  getReservasPorEvento: (id) => axios.get(`${API}/${id}/reservas`).then(res => res.data),
  getEvento: (id) => axios.get(`${API}/${id}`).then(res => res.data),
  editarEstadoReserva: (id, estado) =>
    axios.put(`${API}/${id}/reservas`, JSON.stringify(estado), {
      headers: { 'Content-Type': 'application/json' }
    })
};