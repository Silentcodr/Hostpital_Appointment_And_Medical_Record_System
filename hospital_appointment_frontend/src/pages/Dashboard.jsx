import { useState, useEffect } from 'react';
import { Activity, Users, Calendar, Clock, ArrowRight } from 'lucide-react';
import { useNavigate } from 'react-router-dom';
import { appointmentService, doctorService, patientService, departmentService } from '../services/apiService';

const Dashboard = () => {
    const navigate = useNavigate();
    const [stats, setStats] = useState({
        patients: 0,
        doctors: 0,
        appointmentsTotal: 0,
        availableDepartments: 0
    });
    const [recentAppointments, setRecentAppointments] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        fetchDashboardData();
    }, []);

    const fetchDashboardData = async () => {
        try {
            setLoading(true);
            const [apptRes, docRes, patRes, deptRes] = await Promise.all([
                appointmentService.getAllAppointments(),
                doctorService.getAllDoctors(),
                patientService.getAllPatients(),
                departmentService.getAllDepartments()
            ]);

            const appointments = apptRes?.data || [];
            const doctors = docRes?.data || [];
            const patients = patRes?.data || [];
            const departments = deptRes?.data || [];

            setStats({
                patients: patients.length,
                doctors: doctors.length,
                appointmentsTotal: appointments.length,
                availableDepartments: departments.length
            });

            // Get the 3 most recent appointments
            setRecentAppointments(appointments.slice(-3).reverse());
        } catch (error) {
            console.error('Error fetching dashboard data:', error);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="p-8">
            <div className="page-header">
                <div>
                    <h1 className="page-title">Welcome Back</h1>
                    <p className="page-subtitle">Here is what's happening at the hospital today.</p>
                </div>
                <button className="btn-primary" onClick={() => navigate('/appointments')}>
                    <Calendar size={18} />
                    New Appointment
                </button>
            </div>

            {/* KPI Cards */}
            <div className="dashboard-grid">
                <div className="glass-card stat-card">
                    <div className="stat-icon" style={{ background: 'rgba(99, 102, 241, 0.2)', color: '#6366f1' }}>
                        <Users size={24} />
                    </div>
                    <div className="stat-details">
                        <h3>Total Patients</h3>
                        <h2>{stats.patients}</h2>
                        <span className="trend positive">Registered in system</span>
                    </div>
                </div>

                <div className="glass-card stat-card">
                    <div className="stat-icon" style={{ background: 'rgba(20, 184, 166, 0.2)', color: '#14b8a6' }}>
                        <Activity size={24} />
                    </div>
                    <div className="stat-details">
                        <h3>Available Doctors</h3>
                        <h2>{stats.doctors}</h2>
                        <span className="trend">Currently on shift</span>
                    </div>
                </div>

                <div className="glass-card stat-card">
                    <div className="stat-icon" style={{ background: 'rgba(245, 158, 11, 0.2)', color: '#f59e0b' }}>
                        <Clock size={24} />
                    </div>
                    <div className="stat-details">
                        <h3>Total Appointments</h3>
                        <h2>{stats.appointmentsTotal}</h2>
                        <span className="trend neutral">Scheduled visits</span>
                    </div>
                </div>

                <div className="glass-card stat-card">
                    <div className="stat-icon" style={{ background: 'rgba(239, 68, 68, 0.2)', color: '#ef4444' }}>
                        <Activity size={24} />
                    </div>
                    <div className="stat-details">
                        <h3>Active Departments</h3>
                        <h2>{stats.availableDepartments}</h2>
                        <span className="trend positive">All systems operational</span>
                    </div>
                </div>
            </div>

            <div className="content-grid mt-8">
                <div className="glass-card p-6 main-chart">
                    <h3 className="section-title mb-4">Recent Appointments</h3>
                    {loading ? (
                        <div className="empty-state">
                            <p>Loading recent appointments...</p>
                        </div>
                    ) : recentAppointments.length === 0 ? (
                        <div className="empty-state">
                            <Calendar size={48} className="opacity-50 mb-4" />
                            <p>No recent appointments found.</p>
                        </div>
                    ) : (
                        <div style={{ display: 'flex', flexDirection: 'column', gap: '1rem' }}>
                            {recentAppointments.map(appt => {
                                const date = new Date(appt.appointmentDateTime);
                                const formattedDate = date.toLocaleDateString('en-US', { month: 'short', day: 'numeric', year: 'numeric' });
                                const formattedTime = date.toLocaleTimeString('en-US', { hour: '2-digit', minute: '2-digit' });
                                return (
                                    <div key={appt.appointmentId} style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', padding: '1rem', background: 'rgba(255, 255, 255, 0.05)', borderRadius: '12px', border: '1px solid var(--surface-border)' }}>
                                        <div>
                                            <p style={{ fontWeight: 500, color: 'white' }}>{appt.patient?.patientName || 'Unknown Patient'} <span style={{ color: 'var(--text-secondary)', fontSize: '0.875rem', fontWeight: 400 }}>with Dr. {appt.doctor?.doctorName || 'Unknown'}</span></p>
                                            <p style={{ color: 'var(--text-secondary)', fontSize: '0.875rem' }}>{formattedDate} at {formattedTime}</p>
                                        </div>
                                        <span style={{
                                            padding: '0.25rem 0.75rem', borderRadius: '999px', fontSize: '0.75rem', fontWeight: 600,
                                            backgroundColor: appt.status === 'BOOKED' ? 'rgba(59, 130, 246, 0.2)' :
                                                appt.status === 'COMPLETED' ? 'rgba(34, 197, 94, 0.2)' : 'rgba(239, 68, 68, 0.2)',
                                            color: appt.status === 'BOOKED' ? '#3b82f6' :
                                                appt.status === 'COMPLETED' ? '#22c5e1' : '#ef4444'
                                        }}>
                                            {appt.status}
                                        </span>
                                    </div>
                                );
                            })}
                            <button className="btn-secondary" style={{ marginTop: '1rem', display: 'flex', alignItems: 'center', justifyContent: 'center', gap: '0.5rem' }} onClick={() => navigate('/appointments')}>View All Appointments <ArrowRight size={16} /></button>
                        </div>
                    )}
                </div>
                <div className="glass-card p-6 side-panel">
                    <h3 className="section-title mb-4">Quick Actions</h3>
                    <div className="action-list">
                        <button className="btn-secondary w-full text-left justify-start mb-3" style={{ display: 'flex', alignItems: 'center' }} onClick={() => navigate('/patients')}>
                            <Users size={18} style={{ marginRight: '0.5rem' }} /> Register Patient
                        </button>
                        <button className="btn-secondary w-full text-left justify-start mb-3" style={{ display: 'flex', alignItems: 'center' }} onClick={() => navigate('/doctors')}>
                            <Activity size={18} style={{ marginRight: '0.5rem' }} /> Add Doctor
                        </button>
                        <button className="btn-secondary w-full text-left justify-start" style={{ display: 'flex', alignItems: 'center' }} onClick={() => navigate('/appointments')}>
                            <Calendar size={18} style={{ marginRight: '0.5rem' }} /> View Schedule
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Dashboard;
