import api from '../utils/api';

export const departmentService = {
    getAllDepartments: async () => {
        const response = await api.get('/department');
        return response.data;
    },
    createDepartment: async (departmentData) => {
        const response = await api.post('/department', departmentData);
        return response.data;
    },
    updateDepartment: async (departmentData) => {
        const response = await api.post('/department/update', departmentData);
        return response.data;
    },
    deleteDepartment: async (id) => {
        const response = await api.delete(`/department/${id}`);
        return response.data;
    }
};

export const doctorService = {
    getAllDoctors: async () => {
        const response = await api.get('/doctor');
        return response.data;
    },
    getDoctorById: async (id) => {
        const response = await api.get(`/doctor/${id}`);
        return response.data;
    },
    addDoctor: async (doctorData) => {
        const response = await api.post('/doctor', doctorData);
        return response.data;
    },
    updateDoctor: async (doctorData) => {
        const response = await api.put('/doctor', doctorData);
        return response.data;
    },
    deleteDoctor: async (id) => {
        const response = await api.delete(`/doctor/${id}`);
        return response.data;
    }
};

export const patientService = {
    getAllPatients: async () => {
        const response = await api.get('/patient');
        return response.data;
    },
    registerPatient: async (patientData) => {
        const response = await api.post('/patient', patientData);
        return response.data;
    },
    updatePatient: async (patientData) => {
        const response = await api.put('/patient', patientData);
        return response.data;
    }
};

export const appointmentService = {
    getAllAppointments: async () => {
        const response = await api.get('/appointments');
        return response.data;
    },
    bookAppointment: async (appointmentData) => {
        const response = await api.post('/appointments', appointmentData);
        return response.data;
    },
    updateAppointment: async (appointmentData) => {
        const response = await api.put('/appointments', appointmentData);
        return response.data;
    },
    cancelAppointment: async (id) => {
        const response = await api.delete(`/appointments/${id}`);
        return response.data;
    }
};
